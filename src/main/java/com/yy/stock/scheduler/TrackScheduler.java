package com.yy.stock.scheduler;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.yy.stock.adaptor.amazon.service.AmzOrderItemService;
import com.yy.stock.adaptor.amazon.service.AmzOrdersAddressService;
import com.yy.stock.adaptor.amazon.service.OrdersReportService;
import com.yy.stock.bot.Bot;
import com.yy.stock.dto.TrackRequest;
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.entity.Platform;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.entity.Supplier;
import com.yy.stock.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

;

@Service
@Slf4j
public class TrackScheduler {
    //    @Autowired
//    protected RedissonDistributedLocker distributedLocker;
//    @Autowired
//    protected VisibleStockThreadPoolTaskExecutor executor;
    private boolean isBusy;
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

    @XxlJob(value = "trackJobHandler")
    public void trackXxlJobHandler() throws InterruptedException {
        if (isBusy()) {
            log.info("物流追踪任务正忙，跳过此次计划.");
            return;
        }
        isBusy = true;
        var toTrack = filterUndeliveredOrders();
        schedule(toTrack);
    }

    public List<StockStatus> filterUndeliveredOrders() {
        return stockStatusService.getUndeliveredOrders();
    }

    public void schedule(List<StockStatus> toTrack) {
        log.info("追踪订单个数：" + toTrack.size());
        log.info("开始依次追踪...");
        for (var stock : toTrack) {
            log.info("亚马逊订单号：" + stock.getAmazonOrderId());

            Bot bot = null;
            BuyerAccount buyer = null;
            Platform platform = null;

            try {
                Supplier supplier = supplierService.getById(stock.getSupplierId());
                platform = platformService.getById(supplier.getPlatformId());
                buyer = buyerAccountService.getLeastOrderCountAndNotBuyingBuyer(platform.getId());
                bot = BotFactory.getBot(platform, buyer);
                var trackRequest = new TrackRequest(stock);
                bot.doTrack(trackRequest);
            } catch (Exception ex) {
//                stock.setStatus(StatusEnum.stockFailed.name());
                stock.setLog(ex + Arrays.toString(ex.getStackTrace()));
                stockStatusService.save(stock);
                if (bot != null) {
                    log.info(bot.getBotName() + "开始退出chromedriver.");
                    bot.quitDriver();
                }
//                log.info(getExecutorName(stock) + "bot下单失败,ex:" + ex.getMessage());

            }
        }
    }

    public boolean isBusy() {
        return isBusy;
    }

}
