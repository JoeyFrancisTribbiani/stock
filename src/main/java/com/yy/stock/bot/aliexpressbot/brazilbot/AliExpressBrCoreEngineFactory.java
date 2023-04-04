package com.yy.stock.bot.aliexpressbot.brazilbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.bot.aliexpressbot.AliExpressCoreEngineFactory;
import com.yy.stock.bot.aliexpressbot.engine.loginer.AliExpressLoginEngine;
import com.yy.stock.bot.aliexpressbot.engine.rester.AliExpressResterEngine;
import com.yy.stock.bot.aliexpressbot.engine.stocker.AliExpressStockEngine;
import com.yy.stock.bot.aliexpressbot.engine.tracker.AliExpressTrackEngine;
import com.yy.stock.entity.BuyerAccount;

import java.net.MalformedURLException;

public class AliExpressBrCoreEngineFactory extends AliExpressCoreEngineFactory {
    public AliExpressBrCoreEngineFactory(BuyerAccount buyerAccount) throws MalformedURLException, JsonProcessingException {
        super(buyerAccount);
    }

    @Override
    protected void setCoreEngine() throws MalformedURLException, JsonProcessingException {
        coreEngine = new AliExpressBRCoreEngine(buyerAccount);
    }

    @Override
    public AliExpressBRCoreEngine build() throws JsonProcessingException, MalformedURLException {
        super.build();

        return (AliExpressBRCoreEngine) coreEngine.setLoginEngine(new AliExpressLoginEngine(coreEngine))
                .setStockEngine(new AliExpressStockEngine(coreEngine))
                .setAddressUnit(new AliExpressAddressBRUnit(coreEngine))
                .setResterEngine(new AliExpressResterEngine())
                .setTrackEngine(new AliExpressTrackEngine(coreEngine));

    }

}
