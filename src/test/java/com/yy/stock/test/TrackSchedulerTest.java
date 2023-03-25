package com.yy.stock.test;

import com.yy.stock.adaptor.amazon.api.OrderFulfillmentSubmitFeedService;
import com.yy.stock.adaptor.amazon.service.OrdersReportService;
import com.yy.stock.scheduler.TrackScheduler;
import com.yy.stock.service.BuyerAccountService;
import com.yy.stock.service.StockStatusService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
class TrackSchedulerTest {
    @Autowired
    TrackScheduler trackScheduler;
    @Autowired
    private OrdersReportService ordersReportService;
    @Autowired
    private BuyerAccountService buyerAccountService;
    @Autowired
    private StockStatusService stockStatusService;
    @Autowired
    private OrderFulfillmentSubmitFeedService orderFulfillmentSubmitFeedService;

    @Test
    void testSubmitFeed() throws InterruptedException, DatatypeConfigurationException, ParserConfigurationException, IOException {
        var stockStatus = stockStatusService.getById(BigInteger.valueOf(126));
        orderFulfillmentSubmitFeedService.submit(stockStatus);
    }

    @Test
    void testDoTrack() {
        var list = trackScheduler.filterUndeliveredOrders();
        var tList = list.subList(0, 1);
        trackScheduler.schedule(tList);
    }


}