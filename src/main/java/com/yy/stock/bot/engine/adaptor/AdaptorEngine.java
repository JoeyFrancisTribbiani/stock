package com.yy.stock.bot.engine.adaptor;

import com.yy.stock.adaptor.amazon.api.OrderFulfillmentSubmitFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdaptorEngine {
    @Autowired
    protected OrderFulfillmentSubmitFeedService orderFulfillmentSubmitFeedService;
}
