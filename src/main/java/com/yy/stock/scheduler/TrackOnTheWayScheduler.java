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

import java.util.List;

;

@Service
@Slf4j
public class TrackOnTheWayScheduler {
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

    @XxlJob(value = "trackOnTheWayJobHandler")
    public void trackXxlJobHandler() throws InterruptedException {
        if (isBusy()) {
            log.info("物流追踪任务正忙，跳过此次计划.");
            return;
        }
        isBusy = true;

        try {
            var toTrack = filterUndeliveredOrders();
            schedule(toTrack);
        } catch (Exception ex) {
            log.info("追踪任务过程中报错,ex:" + ex.getMessage());
        } finally {
            isBusy = false;
        }
    }

    public List<StockStatus> filterUndeliveredOrders() {
        return stockStatusService.getOnTheWayOrders();
    }

    public void schedule(List<StockStatus> toTrack) {
        log.info("追踪订单个数：" + toTrack.size());
        log.info("开始依次追踪...");
        var buyerLockKey = "";
        for (var track : toTrack) {
            log.info("亚马逊订单号：" + track.getAmazonOrderId());

            Bot bot = null;
            BuyerAccount buyer = null;
            Platform platform;

            try {
                Supplier supplier = supplierService.getById(track.getSupplierId());
                platform = supplier.getPlatform();

                buyerLockKey = "BUYER_LOCK_KEY-PLATFORM_ID-" + platform.getId();
                distributedLocker.lock(buyerLockKey);
                log.info(getExecutorName(track) + "平台" + platform.getName() + "买家账号加锁成功，开始选择空闲的买家账号追踪");

                try {
                    buyer = buyerAccountService.getById(track.getBuyer().getId());
                    if (buyer.getBotStatus().equals(BotStatus.idle.name())) {
                        buyerAccountService.setBuyerBotStatus(buyer, BotStatus.tracking);
                    } else {
                        throw new NoIdelBuyerAccountException("买家账号" + buyer.getEmail() + "不是空闲状态，无法追踪");
                    }
                } catch (Exception exx) {
                    log.info(getExecutorName(track) + " 未找到空闲买家账号.");
                    throw exx;
                } finally {
                    distributedLocker.unlock(buyerLockKey);
                    log.info(getExecutorName(track) + "平台" + platform.getName() + "解锁，买家账号为：" + (buyer != null ? buyer.getEmail() : "空"));
                }

                bot = botFactory.getBot(buyer);
                var trackRequest = new TrackRequest(track);
                bot.track(trackRequest);
            } catch (Exception ex) {
                track.setLog(ex.getMessage());
                stockStatusService.save(track);
                if (bot != null) {
                    log.info(bot.getBotName() + "开始退出本次追踪任务.");
                }
                if (buyer != null) {
                    buyerAccountService.setBuyerBotStatus(buyer, BotStatus.idle);
                }
            }
        }
    }

    public boolean isBusy() {
        return isBusy;
    }

    public String getExecutorName(StockStatus stockStatus) {
        return "TrackingOnTheWayOrders" + "__" + stockStatus.getAmazonOrderId();
    }
}
