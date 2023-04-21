package com.yy.stock.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.bot.engine.core.BotStatus;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.dto.SkuModuleBase;
import com.yy.stock.dto.StockRequest;
import com.yy.stock.dto.TrackRequest;
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.entity.EmailAccount;
import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
public class Bot {
    protected CoreEngine coreEngine;

    public Bot(CoreEngine coreEngine, BuyerAccount buyerAccount) throws MalformedURLException, JsonProcessingException {
        this.coreEngine = coreEngine;
        this.coreEngine.init(buyerAccount);
    }

    public Bot(CoreEngine coreEngine) throws MalformedURLException, JsonProcessingException {
        this.coreEngine = coreEngine;
    }

    public boolean testAvailable() {
        try {
            var title = coreEngine.getDriverEngine().getDriver().getTitle();
            log.debug("bot测试可用性：true, title: {}", title);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getBotName() {
        return coreEngine.getBotName();
    }

    public BuyerAccount getBotBuyer() {
        return coreEngine.getBuyerAccount();
    }

    public BotStatus getBotStatus() {
        return coreEngine.getBotStatus();
    }

    public String getHomeUrl() {
        return coreEngine.urls.homePage;
    }

    public void register(EmailAccount emailAccount) throws InterruptedException, IOException, MessagingException {
        try {
            coreEngine.register(emailAccount);
        } catch (Exception e) {
            coreEngine.setBotStatus(BotStatus.idle);
            throw e;
        }
    }

    public void login() throws InterruptedException, IOException, MessagingException {
        try {
            coreEngine.login();
        } catch (Exception e) {
            coreEngine.setBotStatus(BotStatus.idle);
            throw e;
        }
    }

    public void stock(StockRequest stockRequest) throws InterruptedException, IOException, MessagingException {
        try {
            coreEngine.stock(stockRequest);
        } catch (Exception e) {
            coreEngine.setBotStatus(BotStatus.idle);
            throw e;
        }
    }

    public void track(TrackRequest trackRequest) throws InterruptedException, DatatypeConfigurationException, ParserConfigurationException, IOException {
        try {
            coreEngine.track(trackRequest);
        } catch (Exception e) {
            coreEngine.setBotStatus(BotStatus.idle);
            throw e;
        }
    }

    public SkuModuleBase fetch(String url) throws InterruptedException, MessagingException, IOException {
        try {
            return coreEngine.fetch(url);
        } catch (Exception e) {
            coreEngine.setBotStatus(BotStatus.idle);
            log.debug("fetch error: {}", e.getMessage());
            return null;
        }
    }

    public void bye() {
        try {
            coreEngine.byebye();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void keepSessionAlive() {
        var curTitle = coreEngine.getDriverEngine().getDriver().getTitle();
        log.info(getBotName() + "当前页面标题: {}", curTitle);
    }
}
