package com.yy.stock.bot.aliexpressbot.brazilbot;

import com.yy.stock.bot.aliexpressbot.engine.core.AliExpressCoreEngine;
import com.yy.stock.bot.aliexpressbot.engine.email.AliExpressEmailEngine;
import com.yy.stock.bot.aliexpressbot.engine.fetcher.AliExpressFetcherEngine;
import com.yy.stock.bot.aliexpressbot.engine.loginer.AliExpressLoginEngine;
import com.yy.stock.bot.aliexpressbot.engine.rester.AliExpressResterEngine;
import com.yy.stock.bot.aliexpressbot.engine.tracker.AliExpressTrackEngine;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressClassSelectors;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressUrls;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressXpaths;
import com.yy.stock.common.util.MySpringUtil;

import java.math.BigDecimal;

public class AliExpressBrCoreEngine extends AliExpressCoreEngine {
    public AliExpressBrCoreEngine() {
        this.xpaths = MySpringUtil.getBean(AliExpressXpaths.class);
        this.urls = MySpringUtil.getBean(AliExpressUrls.class);
        this.classSelector = MySpringUtil.getBean(AliExpressClassSelectors.class);
        //补全剩余属性
        this.resterEngine = new AliExpressResterEngine();
        this.loginEngine = new AliExpressLoginEngine();
        this.stockEngine = new AliExpressBrStockEngine();
        this.trackEngine = new AliExpressTrackEngine();
        this.fetcherEngine = new AliExpressFetcherEngine();
        this.addressEngine = new AliExpressBrAddressEngine();
        this.emailEngine = new AliExpressEmailEngine();
    }

    /**
     * @param text
     * @return
     */
    @Override
    public BigDecimal parseMoney(String text) {
        if (text == null || text.equals("") || text.toLowerCase().equals("free"))
            return new BigDecimal(0);
        text = text.substring(3);
        text = text.replace(',', '.');
        return new BigDecimal(text);
    }
}
