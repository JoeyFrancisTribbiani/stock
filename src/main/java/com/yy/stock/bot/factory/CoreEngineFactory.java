package com.yy.stock.bot.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.entity.BuyerAccount;

import java.net.MalformedURLException;

public abstract class CoreEngineFactory {
    protected BuyerAccount buyerAccount;
    protected CoreEngine coreEngine;

    public CoreEngineFactory(BuyerAccount buyerAccount) throws MalformedURLException, JsonProcessingException {
        this.buyerAccount = buyerAccount;
    }

    public CoreEngine create() throws JsonProcessingException, MalformedURLException {
//        var coreEngine = new CoreEngine(buyerAccount);
//        coreEngine = create(coreEngine);
        setCoreEngine();
        build();
        coreEngine.initialBotCookie();
        return coreEngine;
    }

    protected abstract void setCoreEngine() throws MalformedURLException, JsonProcessingException;

    public abstract CoreEngine build() throws JsonProcessingException, MalformedURLException;
}
