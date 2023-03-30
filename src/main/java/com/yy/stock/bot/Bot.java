package com.yy.stock.bot;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.stock.adaptor.amazon.api.OrderFulfillmentSubmitFeedService;
import com.yy.stock.bot.engine.driver.ChromeDriverEngine;
import com.yy.stock.bot.engine.driver.MyCookie;
import com.yy.stock.common.email.EmailService;
import com.yy.stock.dto.StockRequest;
import com.yy.stock.dto.TrackRequest;
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.service.BuyerAccountService;
import com.yy.stock.service.PlatformService;
import com.yy.stock.service.StockStatusService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;

public abstract class Bot {
    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    protected EmailService emailService;
    @Autowired
    protected BuyerAccountService buyerAccountService;
    @Autowired
    protected PlatformService platformService;
    @Autowired
    protected StockStatusService stockStatusService;
    @Autowired
    protected OrderFulfillmentSubmitFeedService orderFulfillmentSubmitFeedService;
    protected ChromeDriverEngine chromeDriverEngine;
    protected HttpHeaders savedHeaders;
    protected BuyerAccount buyerAccount;
    protected StockRequest stockRequest;
    protected TrackRequest trackRequest;

    protected boolean logined = false;
    // 付款开关，打开后才点击付款按钮，否则跳过付款，直接保存状态为已备货
    @Value("${bot.paySwitch}")
    protected boolean paySwitch;

    public Bot() {
        chromeDriverEngine = new ChromeDriverEngine();
    }

    protected ChromeDriver getDriver() {
        return chromeDriverEngine.getDriver();
    }

    public String getBotName() {
        return null;
    }

    public boolean doStock(StockRequest requestDTO) throws InterruptedException, JsonProcessingException {
        return false;
    }

    public abstract void doTrack(TrackRequest trackRequest) throws IOException, InterruptedException, DatatypeConfigurationException, ParserConfigurationException;


    public void setBuyerAccount(BuyerAccount buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public String getProductHtmlSource(String url) throws IOException, InterruptedException {
        return null;
    }

    public String getSkuProperties(String html) {
        return null;
    }

    public void quitDriver() {
        try {
            chromeDriverEngine.quitDriver();
        } catch (Exception e) {
            e.printStackTrace();
            if (stockRequest != null && buyerAccount.isInBuying()) {
                buyerAccount.setInBuying(false);
                buyerAccountService.save(buyerAccount);
            }
        }
    }


    protected void saveCookiesToBuyerAccount(MyCookie[] cookies) throws JsonProcessingException {
        var cookiesStr = new ObjectMapper().writeValueAsString(cookies);
        buyerAccount.setLoginCookie(cookiesStr);
        buyerAccountService.save(buyerAccount);
    }

    protected void updateHeaders(String key, String value) {
        savedHeaders.set(key, value);
    }

    protected void updateCookiesInHeaders(MyCookie[] cookies) throws JsonProcessingException {
        StringBuilder builder = new StringBuilder();
        for (var cookie : cookies) {
            builder.append(cookie.getName())
                    .append("=")
                    .append(cookie.getValue())
                    .append(";");
        }
        if (builder.isEmpty()) {
            return;
        }
        savedHeaders.set("Cookie", builder.toString());
    }

    protected BigDecimal parseUSDMoney(String text) {
        text = text.substring(4);
        text = text.replace(',', '.');
        return new BigDecimal(text);
    }

    protected void handleSuccessLogin() throws InterruptedException, JsonProcessingException {
        this.logined = true;
        whenSuccessLogin();
        updateCookiesInHeaders(chromeDriverEngine.getDriverCookies());
        saveCookiesToBuyerAccount(chromeDriverEngine.getDriverCookies());
        updateLoginTime();
    }

    protected abstract void whenSuccessLogin() throws InterruptedException;

    protected abstract boolean canLoginUseBuyerCookie() throws InterruptedException, IOException;

    protected void updateLoginTime() {
        buyerAccount.setLastLoginTime(DateTime.now());
        buyerAccountService.save(buyerAccount);
    }
}
