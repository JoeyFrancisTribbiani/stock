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

import java.io.IOException;

public abstract class TrackEngine {
    protected final CoreEngine coreEngine;
    @Resource
    protected GridDriverEngine driverEngine;
    @Resource
    protected OrderFulfillmentSubmitFeedService orderFulfillmentSubmitFeedService;
    protected ResterEngine resterEngine;
    protected LoginEngine loginEngine;
    protected TrackRequest trackRequest;
    protected StockStatusService stockStatusService;

    public TrackEngine(CoreEngine coreEngine) {
        this.coreEngine = coreEngine;
    }

    public void track(TrackRequest trackRequest) {
        this.trackRequest = trackRequest;
        trackLogisticByStockStatus(trackRequest.getStockStatus());
    }

    protected abstract void trackLogisticByStockStatus(StockStatus stockStatus) throws IOException;

    public String getAmazonOrderId() {
        return trackRequest.getStockStatus().getAmazonOrderId();
    }
}
