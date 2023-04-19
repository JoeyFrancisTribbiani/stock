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
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.entity.Platform;
import com.yy.stock.entity.Supplier;
import com.yy.stock.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

;

@Service
@Slf4j
public class FetchScheduler {
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

    @XxlJob(value = "fetchProductAvailableJobHandler")
    public void fetchProductAvailableXxlJobHandler() throws InterruptedException {
        if (isBusy()) {
            log.info("物流追踪任务正忙，跳过此次计划.");
            return;
        }
        isBusy = true;

        try {
            var toFetch = filterAvailableSuppliers();
            schedule(toFetch);
        } catch (Exception ex) {
            log.info("追踪任务过程中报错,ex:" + ex.getMessage());
        } finally {
            isBusy = false;
        }
    }

    public List<Supplier> filterAvailableSuppliers() {
//        return stockStatusService.getOnTheWayOrders();
        return supplierService.getAllAvailableSuppliers();
    }

    public void schedule(List<Supplier> toFetch) {
        log.info("追踪产品个数：" + toFetch.size());
        log.info("开始依次追踪...");
        var buyerLockKey = "";
        for (var fetchable : toFetch) {
            log.info("产品链接：" + fetchable.getUrl());

            Bot bot = null;
            BuyerAccount buyer = null;
            Platform platform = null;

            try {
                platform = fetchable.getPlatform();

                buyerLockKey = "BUYER_LOCK_KEY-PLATFORM_ID-" + platform.getId();
                distributedLocker.lock(buyerLockKey);
                log.info(getExecutorName(fetchable) + "平台" + platform.getName() + "买家账号加锁成功，开始选择Fetcher买家号");

                try {
                    buyer = buyerAccountService.getFetcherAccount(platform.getId());
                    buyerAccountService.setBuyerBotStatus(buyer, BotStatus.fetching);
                } catch (Exception exx) {
                    log.info(getExecutorName(fetchable) + " 未找到空闲fetcher买家账号.");
                    throw new NoIdelBuyerAccountException(getExecutorName(fetchable) + " 未找到空闲fetcher买家账号.");
                } finally {
                    distributedLocker.unlock(buyerLockKey);
                    log.info(getExecutorName(fetchable) + "平台" + platform.getName() + "解锁，买家账号为：" + (buyer != null ? buyer.getEmail() : "空"));
                }


                bot = botFactory.getBot(buyer);
                var html = bot.fetch(fetchable.getUrl());
                if (Objects.equals(html, GlobalVariables.PRODUCT_PAGE_NOT_FOUND)) {
                    //todo 发送邮件提醒

                    fetchable.setAvailable(false);
                    supplierService.save(fetchable);
                }

            } catch (Exception ex) {
                if (buyer != null) {
                    buyerAccountService.setBuyerBotStatus(buyer, BotStatus.idle);
                }
                log.info(getExecutorName(fetchable) + "fetch过程遇到错误:");

//                stock.setStatus(StatusEnum.stockFailed.name());
//                fetchable.setLog(ex + Arrays.toString(ex.getStackTrace()));
//                stockStatusService.save(fetchable);
//                if (bot != null) {
//                    log.info(bot.getBotName() + "开始退出chromedriver.");
//                }
            }
        }
    }

    public boolean isBusy() {
        return isBusy;
    }

    public String getExecutorName(Supplier supplier) {
        return "FetchingProductAvaiable" + "__" + supplier.getName();
    }
}
