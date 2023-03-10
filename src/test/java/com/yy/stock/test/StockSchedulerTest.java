package com.yy.stock.test;

import cn.hutool.core.lang.Assert;
import com.yy.stock.adaptor.amazon.service.OrdersReportService;
import com.yy.stock.common.util.VisibleThreadPoolTaskExecutor;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.scheduler.StockScheduler;
import com.yy.stock.service.BuyerAccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class StockSchedulerTest {
    @Autowired
    private OrdersReportService ordersReportService;

    @Autowired
    private BuyerAccountService buyerAccountService;
    @Autowired
    private StockScheduler stockScheduler;


    @Autowired
    private VisibleThreadPoolTaskExecutor executor;

    @Test
    void testSchedule() throws InterruptedException {
        while (!executor.isEmpty()) {
            Thread.sleep(1);
        }
    }

    @Test
    void testMultiTask() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            if (executor.isFull()) {
                break;
            }
//            stockJob.doStock(i);
        }
        while (!executor.isEmpty()) {
            Thread.sleep(3000);
        }
    }

    @Test
    void testGet9DaysUnshipped() {
        List<OrderItemAdaptorInfoDTO> list = ordersReportService.get9To3DaysUnshippedOrders();
        Assert.notNull(list);
        OrderItemAdaptorInfoDTO o = list.get(0);
        Assert.notNull(o.getId());
        System.out.println(o.getOrderid() + o.getOrderstatus());
        System.out.println(list.size());
    }

    @Test
    void testSyncOrderReportXxlJobHandler() throws IOException {
        stockScheduler.syncOrderReportXxlJobHandler();
    }

    @Test
    void testStockXxlJobHandler() throws InterruptedException {
        stockScheduler.stockXxlJobHandler();
    }

}