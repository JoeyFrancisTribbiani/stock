package com.yy.stock.bot.aliexpressbot.brazilbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.bot.aliexpressbot.engine.core.AliExpressCoreEngine;
import com.yy.stock.entity.BuyerAccount;

import java.math.BigDecimal;
import java.net.MalformedURLException;

public class AliExpressBRCoreEngine extends AliExpressCoreEngine {
    public AliExpressBRCoreEngine(BuyerAccount buyerAccount) throws JsonProcessingException, MalformedURLException {
        super(buyerAccount);
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
