package com.yy.stock.bot.engine.core;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.stock.bot.aliexpressbot.engine.tracker.AliExpressTrackEngine;
import com.yy.stock.bot.engine.driver.GridDriverEngine;
import com.yy.stock.bot.engine.driver.MyCookie;
import com.yy.stock.bot.engine.fetcher.FetcherEngine;
import com.yy.stock.bot.engine.loginer.LoginEngine;
import com.yy.stock.bot.engine.rester.ResterEngine;
import com.yy.stock.bot.engine.stocker.StockEngine;
import com.yy.stock.bot.selector.BaseClassSelector;
import com.yy.stock.bot.selector.BaseUrls;
import com.yy.stock.bot.selector.BaseXpaths;
import com.yy.stock.dto.StockRequest;
import com.yy.stock.dto.TrackRequest;
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.service.BuyerAccountService;
import jakarta.annotation.Resource;

import javax.mail.MessagingException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public abstract class CoreEngine {
    private final BuyerAccount buyerAccount;
    public BaseUrls urls;
    public BaseXpaths xpaths;
    public BaseClassSelector classSelector;
    @Resource
    protected GridDriverEngine driverEngine;
    protected ResterEngine resterEngine;
    protected LoginEngine loginEngine;
    protected StockEngine stockEngine;
    protected AliExpressTrackEngine trackEngine;
    protected FetcherEngine fetcherEngine;
    private BuyerAccountService buyerAccountService;
    private BotStatus botStatus = BotStatus.IDLE;

    public CoreEngine(BuyerAccount buyerAccount) throws JsonProcessingException {
        this.buyerAccount = buyerAccount;
        initialBotCookie();
    }

    public String getBotName() {
        var head = this.getClass().getName() + " IN " + getBotStatus() + " ";
        switch (getBotStatus()) {
            case TRACKING:
                return head + trackEngine.getAmazonOrderId();
            case STOCKING:
                return head + stockEngine.getAmazonOrderId();
            case LOGINING:
                return head + getBuyerInfo();
            case FETCHING: {
                return head + fetcherEngine.getFetchingUrl();
            }
            default:
                return head;
        }
    }

    public BotStatus getBotStatus() {
        return botStatus;
    }

    public void setBotStatus(BotStatus status) {
        this.botStatus = status;
        buyerAccount.setBotStatus(botStatus.name());
        buyerAccountService.save(buyerAccount);
    }

    public void updateBotCookie() throws JsonProcessingException {
        var cookies = driverEngine.getCookie();
        resterEngine.updateCookie(cookies);
        persistBotCookie(cookies);
    }

    public void updateLoginTime() {
        buyerAccount.setLastLoginTime(DateTime.now());
        buyerAccountService.save(buyerAccount);
    }

    private void initialBotCookie() throws JsonProcessingException {
        var cookies = getLoginCookie();
        goHome();
        driverEngine.setOriginCookie(cookies);
        goHome();
        resterEngine.updateCookie(cookies);
    }

    public void persistBotCookie(MyCookie[] cookies) throws JsonProcessingException {
        var cookiesStr = new ObjectMapper().writeValueAsString(cookies);
        buyerAccount.setLoginCookie(cookiesStr);
        buyerAccountService.save(buyerAccount);
    }

    public void goHome() {
        driverEngine.getDriver().get(urls.homePage);
        closeAdPop();
    }

    public BuyerAccount getBuyerAccount() {
        return buyerAccount;
    }

    public String getBuyerInfo() {
        return buyerAccount.getPlatform().getName() + " " + buyerAccount.getEmail() + " " + buyerAccount.getNickname();
    }


    public void login() throws InterruptedException, IOException, MessagingException {
        setBotStatus(BotStatus.LOGINING);
        loginEngine.Login();
        setBotStatus(BotStatus.IDLE);
    }

    public void stock(StockRequest stockRequest) throws InterruptedException, IOException, MessagingException {
        setBotStatus(BotStatus.STOCKING);

        stockEngine.stock(stockRequest);

        buyerAccount.setOrderCount(buyerAccount.getOrderCount() + 1);
        buyerAccountService.save(buyerAccount);
        setBotStatus(BotStatus.IDLE);
    }

    public void track(TrackRequest trackRequest) throws InterruptedException, DatatypeConfigurationException, ParserConfigurationException, IOException {
        setBotStatus(BotStatus.STOCKING);
        trackEngine.track(trackRequest);
        setBotStatus(BotStatus.IDLE);
    }

    public String fetch(String url) throws InterruptedException {
        setBotStatus(BotStatus.FETCHING);
        var html = fetcherEngine.fetch(url);
        setBotStatus(BotStatus.IDLE);
        return html;
    }

    public String getCountryCode() {
        var platform = getBuyerAccount().getPlatform();
        return platform.getCountry();
    }

    public String locateCountryName() {
        var platform = getBuyerAccount().getPlatform();
        var code = platform.getCountry();
        return new Locale("", code).getDisplayCountry();
    }

    public String locateCountryCurrency() {
        var platform = getBuyerAccount().getPlatform();
        var code = platform.getCountry();
        return Currency.getInstance(code).getDisplayName(new Locale("en", code));
    }

    public BigDecimal parseUSDMoney(String text) {
        text = text.substring(4);
        text = text.replace(',', '.');
        return new BigDecimal(text);
    }

    public abstract BigDecimal parseMoney(String text);

    private MyCookie[] getLoginCookie() throws JsonProcessingException {
        String cookiesStr = buyerAccount.getLoginCookie();
        if (cookiesStr != null && !cookiesStr.equals("")) {
            return new ObjectMapper().readValue(cookiesStr, MyCookie[].class);
        }
        return new MyCookie[]{};
    }


    private void closeAdPop() {
        try {
            Thread.sleep(2000L);
            var closeBtn = driverEngine.getExecutor().getByClassName(classSelector.homePageAdCloseButton);
            closeBtn.click();
            Thread.sleep(1000L);
        } catch (Exception ex) {

        }
    }

}
