package com.yy.stock.bot.aliexpressbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressClassSelectors;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressUrls;
import com.yy.stock.bot.aliexpressbot.selector.AliExpressXpaths;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.factory.CoreEngineFactory;
import com.yy.stock.common.util.SpringUtil;
import com.yy.stock.entity.BuyerAccount;

import java.net.MalformedURLException;

public class AliExpressCoreEngineFactory extends CoreEngineFactory {
    public AliExpressCoreEngineFactory(BuyerAccount buyerAccount) throws MalformedURLException, JsonProcessingException {
        super(buyerAccount);
    }

    /**
     * @throws MalformedURLException
     * @throws JsonProcessingException
     */
    @Override
    protected void setCoreEngine() throws MalformedURLException, JsonProcessingException {

    }

    @Override
    public CoreEngine build() throws JsonProcessingException, MalformedURLException {
        return coreEngine.setUrls(SpringUtil.getBean(AliExpressUrls.class))
                .setXpaths(SpringUtil.getBean(AliExpressXpaths.class))
                .setClassSelector(SpringUtil.getBean(AliExpressClassSelectors.class));
    }
}
