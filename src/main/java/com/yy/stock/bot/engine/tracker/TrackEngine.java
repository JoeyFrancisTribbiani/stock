package com.yy.stock.bot.engine.tracker;

import cn.hutool.extra.spring.SpringUtil;
import com.yy.stock.adaptor.amazon.api.OrderFulfillmentSubmitFeedService;
import com.yy.stock.bot.engine.PluggableEngine;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.driver.GridDriverEngine;
import com.yy.stock.bot.engine.loginer.LoginEngine;
import com.yy.stock.bot.engine.rester.ResterEngine;
import com.yy.stock.dto.TrackRequest;
import com.yy.stock.entity.StockStatus;
import com.yy.stock.service.StockStatusService;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

//@Component
//@Scope(value = org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)
public abstract class TrackEngine implements PluggableEngine {
    protected CoreEngine coreEngine;
    protected GridDriverEngine driverEngine;
    protected ResterEngine resterEngine;
    protected LoginEngine loginEngine;

    protected StockStatusService stockStatusService;
    protected OrderFulfillmentSubmitFeedService orderFulfillmentSubmitFeedService;

    protected TrackRequest trackRequest;

    public TrackEngine() {
        this.stockStatusService = SpringUtil.getBean(StockStatusService.class);
        this.orderFulfillmentSubmitFeedService = SpringUtil.getBean(OrderFulfillmentSubmitFeedService.class);
    }

    public void track(TrackRequest trackRequest) throws IOException, DatatypeConfigurationException, InterruptedException, ParserConfigurationException {
        this.trackRequest = trackRequest;
        trackLogisticByStockStatus(trackRequest.getStockStatus());
    }

    protected abstract void trackLogisticByStockStatus(StockStatus stockStatus) throws ParserConfigurationException, IOException, InterruptedException, DatatypeConfigurationException;

    public String getAmazonOrderId() {
        return trackRequest.getStockStatus().getAmazonOrderId();
    }

    @Override
    public void plugIn(CoreEngine plugBaseEngine) {
        this.coreEngine = plugBaseEngine;
        this.loginEngine = coreEngine.getLoginEngine();
        this.driverEngine = coreEngine.getDriverEngine();
        this.resterEngine = coreEngine.getResterEngine();
    }
}