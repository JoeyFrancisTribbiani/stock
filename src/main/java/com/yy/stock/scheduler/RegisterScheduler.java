package com.yy.stock.scheduler;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.yy.stock.adaptor.amazon.service.AmzOrderItemService;
import com.yy.stock.adaptor.amazon.service.AmzOrdersAddressService;
import com.yy.stock.adaptor.amazon.service.OrdersReportService;
import com.yy.stock.bot.Bot;
import com.yy.stock.bot.factory.BotFactory;
import com.yy.stock.common.util.RedissonDistributedLocker;
import com.yy.stock.entity.EmailAccount;
import com.yy.stock.entity.Platform;
import com.yy.stock.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

;

@Service
@Slf4j
public class RegisterScheduler {
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
    @Autowired
    private EmailAccountService emailAccountService;

    @XxlJob(value = "registerJobHandler")
    public void registerXxlJobHandler(BigInteger platformId) throws InterruptedException {
        if (isBusy()) {
            log.info("注册任务正忙，跳过此次计划.");
            return;
        }
        isBusy = true;

        try {
            log.info("开始注册任务...");
            var platform = platformService.getById(platformId);
            List<EmailAccount> toRegister = emailAccountService.getUnregisteredEmail(platform);
            schedule(toRegister, platform);

        } catch (Exception ex) {
            log.info("追踪任务过程中报错,ex:" + ex.getMessage());
        } finally {
            isBusy = false;
        }
    }

    public void schedule(List<EmailAccount> toRegister, Platform platform) {
        log.info("注册邮箱个数：" + toRegister.size());
        log.info("开始依次注册...");
        for (var emailAccount : toRegister) {
            log.info("邮箱：" + emailAccount.getEmail());

            Bot bot = null;
            try {
                bot = botFactory.getRegisterBot(platform);
                bot.register(emailAccount);
            } catch (Exception ex) {
                log.info(getExecutorName(platform) + "__过程遇到错误:");

            }
        }
    }

    public boolean isBusy() {
        return isBusy;
    }

    public String getExecutorName(Platform platform) {
        return "注册账号" + "__" + platform.getName();
    }
}
