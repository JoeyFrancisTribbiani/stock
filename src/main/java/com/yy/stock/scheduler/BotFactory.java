package com.yy.stock.scheduler;

import com.yy.stock.bot.Bot;
import com.yy.stock.common.util.SpringUtil;
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.entity.Platform;

public class BotFactory {
    public static Bot getBot(Platform platform, BuyerAccount buyerAccount) {
        Bot bot = SpringUtil.getBean(platform.getBotBean());
        bot.setBuyerAccount(buyerAccount);
        return bot;
    }
}
