package com.yy.stock.bot.engine.tracker;

import com.yy.stock.adaptor.amazon.api.OrderFulfillmentSubmitFeedService;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.driver.GridDriverEngine;
import com.yy.stock.bot.engine.loginer.LoginEngine;
import com.yy.stock.bot.engine.rester.ResterEngine;
import com.yy.stock.dto.TrackRequest;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.service.StockStatusService;
import jakarta.annotation.Resource;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public abstract class TrackEngine {
    protected final CoreEngine coreEngine;
    protected GridDriverEngine driverEngine;
    @Resource
    protected OrderFulfillmentSubmitFeedService orderFulfillmentSubmitFeedService;
    protected ResterEngine resterEngine;
    protected LoginEngine loginEngine;
    protected TrackRequest trackRequest;
    @Resource
    protected StockStatusService stockStatusService;

    public TrackEngine(CoreEngine coreEngine) {
        this.coreEngine = coreEngine;
        this.driverEngine = coreEngine.getDriverEngine();
        this.resterEngine = coreEngine.getResterEngine();
        this.loginEngine = coreEngine.getLoginEngine();
    }

    public void track(TrackRequest trackRequest) throws IOException, DatatypeConfigurationException, InterruptedException, ParserConfigurationException {
        this.trackRequest = trackRequest;
        trackLogisticByStockStatus(trackRequest.getStockStatus());
    }

    protected abstract void trackLogisticByStockStatus(StockStatus stockStatus) throws ParserConfigurationException, IOException, InterruptedException, DatatypeConfigurationException;

    public String getAmazonOrderId() {
        return trackRequest.getStockStatus().getAmazonOrderId();
    }
}
