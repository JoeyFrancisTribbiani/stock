package com.yy.stock.bot.aliexpressbot.engine.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.entity.BuyerAccount;

public abstract class AliExpressCoreEngine extends CoreEngine {
    public AliExpressCoreEngine(BuyerAccount buyerAccount) throws JsonProcessingException {
        super(buyerAccount);
    }
}
