package com.yy.stock.scheduler;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.yy.stock.adaptor.amazon.service.AmzOrderItemService;
import com.yy.stock.adaptor.amazon.service.AmzOrdersAddressService;
import com.yy.stock.adaptor.amazon.service.OrdersReportService;
import com.yy.stock.common.util.FtpUtil;
import com.yy.stock.common.util.RedissonDistributedLocker;
import com.yy.stock.common.util.VisibleThreadPoolTaskExecutor;
import com.yy.stock.config.GlobalVariables;
import com.yy.stock.config.StatusEnum;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.dto.StockInfoDTO;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.entity.SyncOrder;
import com.yy.stock.service.BuyerAccountService;
import com.yy.stock.service.StockStatusService;
import com.yy.stock.service.SupplierService;
import com.yy.stock.service.SyncOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

;

@Service
@Slf4j
public class StockScheduler {
    @Autowired
    protected RedissonDistributedLocker distributedLocker;
    @Autowired
    protected VisibleThreadPoolTaskExecutor executor;
    @Autowired
    private AmzOrderItemService amzOrderItemService;
    @Autowired
    private OrdersReportService ordersReportService;
    @Autowired
    private StockStatusService stockStatusService;
    @Autowired
    private BuyerAccountService buyerAccountService;
    @Autowired
    private AmzOrdersAddressService amzOrdersAddressService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private StockAsyncExecutor stockAsyncExecutor;
    @Autowired
    private SyncOrderService syncOrderService;

    @NotNull
    private static String[] getTimestampFromName(FTPFile ftpFile) {
        var nameWords = ftpFile.getName().split("-");
        var withExt = nameWords[1].split("\\.");
        nameWords[1] = withExt[0];
        return nameWords;
    }

    @XxlJob(value = "stockJobHandler")
    public void stockXxlJobHandler() throws InterruptedException {
        distributedLocker.lock(GlobalVariables.SCHEDULE_ORDER_LOCK_KEY);
        System.out.println("????????? ?????????????????????????????????");

        List<StockInfoDTO> toStock = filterUnstockedOrderItems();

        distributedLocker.unlock(GlobalVariables.SCHEDULE_ORDER_LOCK_KEY);
        System.out.println("????????? ??????");

        schedule(toStock);
    }

    @XxlJob(value = "syncOrderReportJobHandler")
    public void syncOrderReportXxlJobHandler() throws IOException {
        List<SyncOrder> providers = syncOrderService.getOpeningServers();
        for (var provider : providers) {
            FtpUtil f = new FtpUtil(true);
            if (f.login(provider.getSyncHost(), provider.getSyncPort(), "anonymous", null)) {
                f.List("/", "txt");
            }
            List<FTPFile> toSync = new ArrayList<>();
            BigInteger lastUpdateTimeStamp;
            for (var ftpFile : f.remoteFiles) {

                // ????????????????????????????????????
                // ??????????????????-?????????.txt
                String[] nameWords = getTimestampFromName(ftpFile);
                if (nameWords[0].equals(provider.getReportNamePrefix())) {
                    var timeStamp = new BigInteger(nameWords[1]);
                    lastUpdateTimeStamp = provider.getLastUpdateTimestamp();
                    if (timeStamp.compareTo(lastUpdateTimeStamp) == 1) {
                        toSync.add(ftpFile);
                    }
                }
            }
            for (var sFile : toSync) {
                Closeable closeThis = null;
                try {
                    InputStream inputStream = f.ftp.retrieveFileStream(sFile.getName());
                    closeThis = inputStream;
                    var charset = Charset.forName("utf-8");
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
                    closeThis = inputStreamReader;
                    BufferedReader br = new BufferedReader(inputStreamReader);

                    ordersReportService.treatReportTxt(provider, br);

                    String[] nameWords = getTimestampFromName(sFile);
                    lastUpdateTimeStamp = new BigInteger(nameWords[1]);
                    provider.setLastUpdateTimestamp(lastUpdateTimeStamp);
                    syncOrderService.save(provider);
                    inputStream.close();
                    f.ftp.completePendingCommand();

                } catch (Exception ex) {
                    log.error("");

                } finally {
                    closeThis.close();
                }
            }
            f.disConnection();

        }
    }

    public List<StockInfoDTO> filterUnstockedOrderItems() {
        List<StockInfoDTO> toStock = new ArrayList<>();

        if (isBusy()) {
            return toStock;
        }

        List<OrderItemAdaptorInfoDTO> unshippedIn3Days = amzOrderItemService.get3DaysUnshipped();
        List<OrderItemAdaptorInfoDTO> unshippedIn9To3Days = ordersReportService.get9To3DaysUnshippedOrders();
        unshippedIn9To3Days.addAll(unshippedIn3Days);


        int count = 0;
        for (OrderItemAdaptorInfoDTO order : unshippedIn9To3Days) {
            var address = amzOrdersAddressService.getByOrderInfo(order);
            if (address == null || address.getName() == null || address.getName().equals("")) {
                continue;
            }
            StockStatus stockStatus = stockStatusService.getOrCreateByOrderItemInfo(order);
            if (stockStatus.getStatus().equals(StatusEnum.unstocked.name())) {
                count++;
                if (count > capacity()) {
                    break;
                }

                stockStatus.setStatus(StatusEnum.stocking.name());
                stockStatusService.save(stockStatus);

                StockInfoDTO stockInfo = new StockInfoDTO();
                stockInfo.setOrderItemAdaptorInfo(order);
                stockInfo.setStockStatus(stockStatus);

                toStock.add(stockInfo);
            }
        }
        return toStock;
    }

    public void schedule(List<StockInfoDTO> toStock) {
        for (StockInfoDTO stockInfo : toStock) {
            stockAsyncExecutor.startStockAsync(stockInfo.getOrderItemAdaptorInfo(), stockInfo.getStockStatus());
        }
    }

    public boolean isBusy() {
        return executor.isFull();
    }

    public int capacity() {
        return executor.getIdleCount();
    }

//    public void autoStock() {
//        List<OrdersReportDTO> unshiped = ordersReportService.getUnshiped();
//        for (OrdersReportDTO ordersReportDTO : unshiped) {
//            BuyerAccountDTO buyer = buyerAccountService.getNewestBuyer();
//            AmzOrdersAddressDTO ordersAddress = amzOrdersAddressService.getByOrderIdAndAuthId(ordersReportDTO.getAmazonOrderId(), ordersReportDTO.getAmazonAuthId());
//            SupplierDTO supplier = supplierService.getByAmazonOrderInfo(ordersReportDTO);
//            StockRequest stockRequest = new StockRequest(ordersReportDTO, supplier, buyer, ordersAddress);
//            System.out.println(ordersAddress);
//        }
//    }


}
