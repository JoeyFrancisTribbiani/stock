package com.yy.stock.test;

import com.yy.stock.adaptor.amazon.api.AmazonClientOneFeign;
import com.yy.stock.adaptor.amazon.api.OrderFulfillmentSubmitFeedService;
import com.yy.stock.adaptor.amazon.service.OrdersReportService;
import com.yy.stock.scheduler.TrackOnTheWayScheduler;
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
class TrackOnTheWaySchedulerTest {
    @Autowired
    TrackOnTheWayScheduler trackOnTheWayScheduler;
    @Autowired
    private OrdersReportService ordersReportService;
    @Autowired
    private BuyerAccountService buyerAccountService;
    @Autowired
    private StockStatusService stockStatusService;
    @Autowired
    private OrderFulfillmentSubmitFeedService orderFulfillmentSubmitFeedService;
    @Autowired
    private AmazonClientOneFeign amazonClientOneFeign;

    @Test
    void testSubmitFeed() throws InterruptedException, DatatypeConfigurationException, ParserConfigurationException, IOException {
        var stockStatus = stockStatusService.getById(BigInteger.valueOf(126));
        orderFulfillmentSubmitFeedService.submit(stockStatus);
    }

    // test getSubmitFeedInfo()
    @Test
    void testGetSubmitFeedInfo() {
        amazonClientOneFeign.getSubmitFeedInfo("100232770256638954");
    }

    @Test
    void testProcess() {
        amazonClientOneFeign.processSubmitFeed();
    }

    @Test
    void testRefreshOrderIndays() {
        for (int i = 0; i < 6; i++) {
            amazonClientOneFeign.refreshOrderInDays(6);
        }
    }

    @Test
    void testRefreshAllOrdersIndays() {
        for (int i = 0; i < 6; i++) {
            amazonClientOneFeign.refreshAllOrdersInDays(9);
        }
    }

    //    @Test
//    void testGetOneOrder() {
//        for (int i = 0; i < 6; i++) {
//            amazonClientOneFeign.refreshOrderInDays(6);
//        }
//    }
    @Test
    void testGetOrderReport7Days() {
        //GET_FLAT_FILE_ALL_ORDERS_DATA_BY_ORDER_DATE_GENERAL
        amazonClientOneFeign.refreshOrderReport7Days("GET_FLAT_FILE_ALL_ORDERS_DATA_BY_LAST_UPDATE_GENERAL");
    }

    @Test
    void testRetrunOrderReport10Days() {
        amazonClientOneFeign.refreshOrderReport7Days("GET_FLAT_FILE_RETURNS_DATA_BY_RETURN_DATE");
    }

    @Test
    void testGetOrderItemInfo() {
        amazonClientOneFeign.getOrderItemInfo("702-2928229-8561828");
        amazonClientOneFeign.getOrderItemInfo("701-2113255-9957040");
    }


    @Test
    void testSummary() {
        amazonClientOneFeign.summaryOrderReport();

    }

    @Test
    void testDoTrack() {
        var list = trackOnTheWayScheduler.filterUndeliveredOrders();
        var tList = list.subList(0, 1);
        trackOnTheWayScheduler.schedule(tList);
    }


}