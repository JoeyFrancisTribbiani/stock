package com.yy.stock.bot.amazonbot.singaporebot;

import com.yy.stock.bot.amazonbot.engine.core.AmazonCoreEngine;

import java.math.BigDecimal;

public class AmazonSgpCoreEngine extends AmazonCoreEngine {
    public AmazonSgpCoreEngine()  {
        this.fetcherEngine= new AmazonSgpFetcherEngine();
        this.fetcherEngine.assmble(this);
    }

    /**
     * @param text
     * @return
     */
    public BigDecimal parseMoney(String text) {
        if (text == null || text.equals("") || text.toLowerCase().equals("free"))
            return new BigDecimal(0);
        text = text.substring(4);
        text = text.replace(',', '.');
        return new BigDecimal(text);
    }
}
