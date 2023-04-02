package com.yy.stock.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.bot.engine.core.BotStatus;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.driver.GridDriverEngine;
import com.yy.stock.bot.engine.email.EmailEngine;
import com.yy.stock.bot.engine.rester.ResterEngine;
import com.yy.stock.dto.StockRequest;
import com.yy.stock.dto.TrackRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;

public abstract class Bot {
    @Autowired
    protected EmailEngine emailEngine;
    @Autowired
    protected GridDriverEngine driverEngine;
    protected CoreEngine coreEngine;
    protected ResterEngine resterEngine;

    public Bot() throws MalformedURLException, JsonProcessingException {
    }

    public String getBotName() {
        return coreEngine.getBotName();
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

    public String fetch(String url) throws InterruptedException {
        return coreEngine.fetch(url);
    }

    public void bye() {
        try {
            driverEngine.quitDriver();
        } catch (Exception e) {
            e.printStackTrace();
//            if (stock stockRequest != null && buyerAccount.isInBuying()) {
//                buyerAccount.setInBuying(false);
//                buyerAccountService.save(buyerAccount);
//            }
        }
    }

}
