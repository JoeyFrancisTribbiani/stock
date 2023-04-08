package com.yy.stock.scheduler;

import com.yy.stock.adaptor.amazon.service.AmzOrderItemService;
import com.yy.stock.adaptor.amazon.service.AmzOrdersAddressService;
import com.yy.stock.adaptor.amazon.service.OrdersReportService;
import com.yy.stock.common.util.RedissonDistributedLocker;
import com.yy.stock.common.util.VisibleStockThreadPoolTaskExecutor;
import com.yy.stock.config.GlobalVariables;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.dto.StockInfoDTO;
import com.yy.stock.dto.StockStatusEnum;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

;

@Service
@Slf4j
public class StockScheduler {
    @Autowired
    protected RedissonDistributedLocker distributedLocker;
    @Autowired
    protected VisibleStockThreadPoolTaskExecutor executor;
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
    private PlatformService platformService;
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

    //    @XxlJob(value = "stockJobHandler")
    public void stockXxlJobHandler() throws InterruptedException {
        distributedLocker.lock(GlobalVariables.SCHEDULE_ORDER_LOCK_KEY);
        log.info("本线程 加锁成功，开始选择订单");
        List<StockInfoDTO> toStock = null;
        try {
            toStock = filterUnstockedOrderItems();
            log.info("选择订单个数：" + toStock.size());
            log.info("订单号为：");
            for (var o : toStock) {
                log.info(o.getOrderItemAdaptorInfoDTO().getOrderid());
            }
        } catch (Exception ex) {
            log.info("筛选未备货订单时出错，跳过本次任务.");
            return;
        } finally {
            distributedLocker.unlock(GlobalVariables.SCHEDULE_ORDER_LOCK_KEY);
            log.info("本线程 解锁");
        }

        schedule(toStock);
    }

    public List<StockInfoDTO> filterUnstockedOrderItems() {
        List<StockInfoDTO> toStock = new ArrayList<>();

        if (isBusy()) {
            return toStock;
        }

        List<OrderItemAdaptorInfoDTO> unshippedIn3Days = amzOrderItemService.get6DaysUnshipped();
        log.info("6天内的订单个数：" + unshippedIn3Days.size());
        List<OrderItemAdaptorInfoDTO> unshippedIn9To3Days = ordersReportService.get9To6DaysUnshippedOrders();
        log.info("9到6天的订单个数：" + unshippedIn9To3Days.size());
        unshippedIn9To3Days.addAll(unshippedIn3Days);


//        List<OrderItemAdaptorInfoDTO> singleList = new ArrayList<>();
//        for (var o : unshippedIn9To3Days) {
//            var sku = o.getSku();
//            if (sku.equals("VN-22V2-1Y0D")) {
//                singleList = Collections.singletonList(o);
//                break;
//            }
//        }
////        var testSingle = unshippedIn9To3Days.stream().filter(s -> s.getSku() == "VN-22V2-1Y0D").findFirst().orElse(null);
//        unshippedIn9To3Days = singleList;


        int count = 0;
        for (OrderItemAdaptorInfoDTO order : unshippedIn9To3Days) {
            var address = amzOrdersAddressService.getByOrderInfo(order);
            if (address == null || address.getName() == null || address.getName().equals("")) {
                continue;
            }
            StockStatus stockStatus = stockStatusService.getOrCreateByOrderItemSku(order);

            // 异常订单标记为stockful=false，不发货
            if (!stockStatus.getStockful()) {
                continue;
            }

            if (stockStatus.getStatus().equals(StockStatusEnum.unstocked.name()) ||
                    stockStatus.getStatus().equals(StockStatusEnum.stockFailed.name())) {
                count++;
                if (count > capacity()) {
                    break;
                }

                stockStatus.setStatus(StockStatusEnum.stocking.name());
                stockStatusService.save(stockStatus);

                StockInfoDTO stockInfo = new StockInfoDTO();
                stockInfo.setOrderItemAdaptorInfoDTO(order);
                stockInfo.setStockStatus(stockStatus);

                toStock.add(stockInfo);
            }
        }
        return toStock;
    }

    public void schedule(List<StockInfoDTO> toStock) {
        for (StockInfoDTO stockInfo : toStock) {
            stockAsyncExecutor.startStockAsync(stockInfo.getOrderItemAdaptorInfoDTO(), stockInfo.getStockStatus());
        }
    }

    public boolean isBusy() {
        return executor.isFull();
    }

    public int capacity() {
        return executor.getIdleCount();
    }

}
