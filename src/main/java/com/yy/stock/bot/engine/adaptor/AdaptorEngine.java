package com.yy.stock.bot.engine.adaptor;

import com.yy.stock.adaptor.amazon.api.OrderFulfillmentSubmitFeedService;
import com.yy.stock.bot.engine.PluggableEngine;
import com.yy.stock.bot.engine.core.CoreEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)


public class AdaptorEngine implements PluggableEngine {
    @Autowired
    public OrderFulfillmentSubmitFeedService orderFulfillmentSubmitFeedService;

    /**
     * @param plugBaseEngine
     */
    @Override
    public void plugIn(CoreEngine plugBaseEngine) {

    }
}
