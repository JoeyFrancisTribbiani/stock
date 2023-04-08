package com.yy.stock.scheduler;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.yy.stock.adaptor.amazon.service.AmzOrderItemService;
import com.yy.stock.adaptor.amazon.service.AmzOrdersAddressService;
import com.yy.stock.adaptor.amazon.service.OrdersReportService;
import com.yy.stock.bot.Bot;
import com.yy.stock.bot.engine.core.BotStatus;
import com.yy.stock.bot.factory.BotFactory;
import com.yy.stock.common.exception.NoIdelBuyerAccountException;
import com.yy.stock.common.util.RedissonDistributedLocker;
import com.yy.stock.config.GlobalVariables;
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
public class TrackUnshippedScheduler {
    @Autowired
    protected BotFactory botFactory;
    @Autowired
    protected RedissonDistributedLocker distributedLocker;
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

    @XxlJob(value = "trackUnshippedJobHandler")
    public void trackXxlJobHandler() throws InterruptedException {
        if (isBusy()) {
            log.info("物流追踪任务正忙，跳过此次计划.");
            return;
        }
        isBusy = true;
        try {
            var toTrack = filterUnshippedOrders();
            schedule(toTrack);
        } catch (Exception ex) {
            log.info("追踪任务过程中报错,ex:" + ex.getMessage());
        } finally {
            isBusy = false;
        }
    }

    public List<StockStatus> filterUnshippedOrders() {
        return stockStatusService.getStockedUnshippedOrders();
    }

    public void schedule(List<StockStatus> toTrack) {
        log.info("追踪订单个数：" + toTrack.size());
        log.info("开始依次追踪...");
        var buyerLockKey = "";
        for (var stock : toTrack) {
            log.info("亚马逊订单号：" + stock.getAmazonOrderId());

            Bot bot = null;
            BuyerAccount buyer = null;
            Platform platform = null;

            try {
                Supplier supplier = supplierService.getById(stock.getSupplierId());
                platform = platformService.getById(supplier.getPlatformId());

                buyerLockKey = GlobalVariables.PLATFORM_ALL_BUYERS_LOCK_KEY_HEADER + platform.getId();
                distributedLocker.lock(buyerLockKey);
                log.info(getExecutorName(stock) + "平台" + platform.getName() + "买家账号加锁成功，开始选择空闲的买家账号下单");

                try {
                    buyer = buyerAccountService.getEarliestLoginedIdleBuyer(platform.getId());
                    buyerAccountService.setBuyerBotStatus(buyer, BotStatus.tracking);
                } catch (Exception exx) {
                    log.info(getExecutorName(stock) + " 未找到空闲买家账号.");
                    throw new NoIdelBuyerAccountException(getExecutorName(stock) + " 未找到空闲买家账号.");
                } finally {
                    distributedLocker.unlock(buyerLockKey);
                    log.info(getExecutorName(stock) + "平台" + platform.getName() + "解锁，买家账号为：" + (buyer != null ? buyer.getEmail() : "空"));
                }

                buyer = buyerAccountService.getLeastOrderCountAndIdleBuyer(platform);
                bot = botFactory.getBot(buyer);
                var trackRequest = new TrackRequest(stock);
                bot.track(trackRequest);
            } catch (Exception ex) {
//                stock.setStatus(StatusEnum.stockFailed.name());
                stock.setLog(ex + Arrays.toString(ex.getStackTrace()));
                stockStatusService.save(stock);
                if (bot != null) {
                    log.info(bot.getBotName() + "开始退出chromedriver.");
                }
                log.info("追踪任务过程中报错,ex:" + ex.getMessage());
            }
        }
    }

    public boolean isBusy() {
        return isBusy;
    }

    public String getExecutorName(StockStatus stockStatus) {
        return "TrackingUnshippedOrders" + "__" + stockStatus.getAmazonOrderId();
    }
}
