package com.yy.stock.bot.aliexpressbot.brazilbot;

import com.yy.stock.bot.aliexpressbot.engine.core.AliExpressCoreEngine;
import com.yy.stock.bot.aliexpressbot.engine.fetcher.AliExpressFetcherEngine;
import com.yy.stock.bot.aliexpressbot.engine.loginer.AliExpressLoginEngine;
import com.yy.stock.bot.aliexpressbot.engine.rester.AliExpressResterEngine;
import com.yy.stock.bot.aliexpressbot.engine.stocker.AliExpressStockEngine;
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
        this.resterEngine = MySpringUtil.getBean(AliExpressResterEngine.class);
        //补全剩余属性
        this.loginEngine = new AliExpressLoginEngine();
        this.stockEngine = new AliExpressStockEngine();
        this.trackEngine = new AliExpressTrackEngine();
        this.fetcherEngine = new AliExpressFetcherEngine();
        this.addressEngine = new AliExpressBrAddressEngine();
    }

    /**
     * @param text
     * @return
     */
    @Override
    public BigDecimal parseMoney(String text) {
        text = text.substring(3);
        text = text.replace(',', '.');
        return new BigDecimal(text);
    }
}
