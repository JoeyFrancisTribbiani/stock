package com.yy.stock.scheduler;

import com.xxl.job.core.handler.annotation.XxlJob;
import com.yy.stock.adaptor.amazon.service.AmzOrderItemService;
import com.yy.stock.adaptor.amazon.service.AmzOrdersAddressService;
import com.yy.stock.adaptor.amazon.service.OrdersReportService;
import com.yy.stock.bot.factory.BotFactory;
import com.yy.stock.common.util.RedissonDistributedLocker;
import com.yy.stock.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

;

@Service
@Slf4j
public class KeepSessionsAliveScheduler {
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

    @XxlJob(value = "keepSessionsAliveJobHandler")
    public void keepSessionsAliveXxlJobHandler() throws InterruptedException {
        if (isBusy()) {
            log.info("保活任务正忙，跳过此次计划.");
            return;
        }
        isBusy = true;
        try {
            log.info("保活任务开始.");
            botFactory.keepSessionsAlive();
        } catch (Exception ex) {
            log.info("追踪任务过程中报错,ex:" + ex.getMessage());
        } finally {
            isBusy = false;
        }
    }

    public boolean isBusy() {
        return isBusy;
    }
}
