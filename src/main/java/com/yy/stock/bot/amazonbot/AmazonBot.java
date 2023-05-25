package com.yy.stock.bot.amazonbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.bot.amazonbot.engine.core.AmazonCoreEngine;
import com.yy.stock.bot.engine.core.BotStatus;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@Getter
public class AmazonBot {
    public AmazonCoreEngine coreEngine;

    public AmazonBot(AmazonCoreEngine coreEngine) throws MalformedURLException, JsonProcessingException {
        this.coreEngine = coreEngine;
    }
    public BotStatus getBotStatus() {
        return coreEngine.getBotStatus();
    }
    public String getBotName(){
        return coreEngine.getBotName();
    }

    public void fetch(String url) throws InterruptedException, MessagingException, IOException {
        try {
            coreEngine.fetch(url);
        } catch (Exception e) {
            coreEngine.setBotStatus(BotStatus.idle);
            log.debug("fetch error: {}", e.getMessage());
        }
    }

}
