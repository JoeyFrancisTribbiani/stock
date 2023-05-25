package com.yy.stock.bot.amazonbot.engine.core;

import com.yy.stock.bot.amazonbot.engine.fetcher.AmazonFetcherEngine;
import com.yy.stock.bot.amazonbot.engine.rester.AmazonResterEngine;
import com.yy.stock.bot.engine.core.BotStatus;
import lombok.Getter;

import javax.mail.MessagingException;
import java.io.IOException;

@Getter
public abstract class AmazonCoreEngine  {
    public  AmazonFetcherEngine fetcherEngine;
    protected AmazonResterEngine resterEngine;
    protected BotStatus botStatus = BotStatus.idle;

    public String getBotName(){
        return this.fetcherEngine.getBotNameFromFetchingUrl();
    }
    public void fetch(String url) throws InterruptedException, MessagingException, IOException {
        fetcherEngine.fetchAsync(url);
    }
    public BotStatus getBotStatus() {
        return botStatus;
    }

    public void setBotStatus(BotStatus status) {
        this.botStatus = status;
    }

}
