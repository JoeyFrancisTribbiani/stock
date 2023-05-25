package com.yy.stock.bot.amazonbot.singaporebot;

import com.yy.stock.bot.amazonbot.engine.rester.AmazonResterEngine;
import com.yy.stock.bot.engine.driver.MyCookie;

public class AmazonSgpResterEngine extends AmazonResterEngine {
    public AmazonSgpResterEngine() {
        super();
        var cookie = new MyCookie("i18n-prefs", "SGD");
        this.updateCookie(new MyCookie[]{cookie});
    }
}
