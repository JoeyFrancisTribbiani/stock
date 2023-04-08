package com.yy.stock.test;

import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.adaptor.amazon.service.OrdersReportService;
import com.yy.stock.bot.factory.BotFactory;
import com.yy.stock.common.util.VisibleStockThreadPoolTaskExecutor;
import com.yy.stock.dto.OrderItemAdaptorInfoDTO;
import com.yy.stock.scheduler.StockScheduler;
import com.yy.stock.service.BuyerAccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class StockSchedulerTest {
    @Autowired
    private BotFactory botFactory;
    @Autowired
    private OrdersReportService ordersReportService;

    @Autowired
    private BuyerAccountService buyerAccountService;
    @Autowired
    private StockScheduler stockScheduler;


    @Autowired
    private VisibleStockThreadPoolTaskExecutor executor;

    @Test
    void testSchedule() throws InterruptedException {
    }

    @Test
    void testBotFactory() throws InterruptedException, MalformedURLException, JsonProcessingException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var buyer = buyerAccountService.getById(BigInteger.valueOf(1));
        var bot = botFactory.getBot(buyer);
        System.out.println(bot.getBotName());
    }


    @Test
    void testGet9DaysUnshipped() {
        List<OrderItemAdaptorInfoDTO> list = ordersReportService.get9To6DaysUnshippedOrders();
        Assert.notNull(list);
        OrderItemAdaptorInfoDTO o = list.get(0);
        Assert.notNull(o.getId());
        System.out.println(o.getOrderid() + o.getOrderstatus());
        System.out.println(list.size());
    }


    @Test
    void testStockXxlJobHandler() throws InterruptedException {
        stockScheduler.stockXxlJobHandler();
    }

}