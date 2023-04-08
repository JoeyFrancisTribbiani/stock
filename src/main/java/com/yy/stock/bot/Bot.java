package com.yy.stock.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.bot.engine.core.BotStatus;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.dto.StockRequest;
import com.yy.stock.dto.TrackRequest;
import com.yy.stock.entity.BuyerAccount;
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
//        coreEngine.assemble();
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

    public void login() throws InterruptedException, IOException, MessagingException {
        coreEngine.login();
    }

    public void stock(StockRequest stockRequest) throws InterruptedException, IOException, MessagingException {
        coreEngine.stock(stockRequest);
    }

    public void track(TrackRequest trackRequest) throws InterruptedException, DatatypeConfigurationException, ParserConfigurationException, IOException {
        coreEngine.track(trackRequest);
    }

    public String fetch(String url) throws InterruptedException, MessagingException, IOException {
        return coreEngine.fetch(url);
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
        log.info(getBotName() + "Current title: {}", curTitle);
    }
}
