package com.yy.stock.bot.engine.core;

import cn.hutool.core.date.DateTime;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.stock.bot.engine.driver.GridDriverEngine;
import com.yy.stock.bot.engine.driver.MyCookie;
import com.yy.stock.bot.engine.email.EmailEngine;
import com.yy.stock.bot.engine.fetcher.FetcherEngine;
import com.yy.stock.bot.engine.loginer.LoginEngine;
import com.yy.stock.bot.engine.rester.ResterEngine;
import com.yy.stock.bot.engine.stocker.AddressEngine;
import com.yy.stock.bot.engine.stocker.StockEngine;
import com.yy.stock.bot.engine.tracker.TrackEngine;
import com.yy.stock.bot.selector.BaseClassSelector;
import com.yy.stock.bot.selector.BaseUrls;
import com.yy.stock.bot.selector.BaseXpaths;
import com.yy.stock.dto.StockRequest;
import com.yy.stock.dto.TrackRequest;
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.service.BuyerAccountService;
import lombok.Getter;

import javax.mail.MessagingException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Currency;
import java.util.Locale;

//@Accessors(chain = true)
@Getter
//@Setter
//@Component
//@Scope(value = org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS)

public abstract class CoreEngine {
    public BaseUrls urls;
    public BaseXpaths xpaths;
    public BaseClassSelector classSelector;
    protected GridDriverEngine driverEngine;
    protected ResterEngine resterEngine;
    protected LoginEngine loginEngine;
    protected EmailEngine emailEngine;
    protected StockEngine stockEngine;
    protected AddressEngine addressEngine;
    protected TrackEngine trackEngine;
    protected FetcherEngine fetcherEngine;
    private BuyerAccount buyerAccount;
    private BuyerAccountService buyerAccountService;
    private BotStatus botStatus = BotStatus.idle;

//    public CoreEngine() throws JsonProcessingException, MalformedURLException {
//        this.driverEngine = new GridDriverEngine();
//        this.buyerAccountService = new BuyerAccountService();
//        this.buyerAccountService = SpringUtil.getBean(BuyerAccountService.class);
//        this.driverEngine = SpringUtil.getBean(GridDriverEngine.class);
//        initialBotCookie();
//    }

    public String getBotName() {
        //获取非全限定类名
        //return this.getClass().getSimpleName();
        var head = this.getClass().getSimpleName() + " IN " + getBotStatus() + " ";
        switch (getBotStatus()) {
            case tracking:
                return head + trackEngine.getAmazonOrderId();
            case stocking:
                return head + stockEngine.getAmazonOrderId();
            case logining:
                return head + getBuyerInfo();
            case fetching: {
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

    public void initialBotCookie() throws JsonProcessingException {
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
        setBotStatus(BotStatus.logining);
        loginEngine.login();
        setBotStatus(BotStatus.idle);
    }

    public void stock(StockRequest stockRequest) throws InterruptedException, IOException, MessagingException {
//        setBotStatus(BotStatus.STOCKING);

        stockEngine.stock(stockRequest);

        buyerAccount.setOrderCount(buyerAccount.getOrderCount() + 1);
        buyerAccountService.save(buyerAccount);
        setBotStatus(BotStatus.idle);
    }

    public void track(TrackRequest trackRequest) throws InterruptedException, DatatypeConfigurationException, ParserConfigurationException, IOException {
        setBotStatus(BotStatus.stocking);
        trackEngine.track(trackRequest);
        setBotStatus(BotStatus.idle);
    }

    public String fetch(String url) throws InterruptedException, MessagingException, IOException {
        setBotStatus(BotStatus.fetching);
        var html = fetcherEngine.fetch(url);
        setBotStatus(BotStatus.idle);
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
//        return Currency.getInstance(code).getDisplayName(new Locale("en", code));
        return Currency.getInstance(new Locale("en", code)).getCurrencyCode();
    }

    public BigDecimal parseUSDMoney(String text) {
        text = text.substring(4);
        text = text.replace(',', '.');
        return new BigDecimal(text);
    }

    public BigDecimal parseMoney(String text) {
        return parseUSDMoney(text);
    }

    private MyCookie[] getLoginCookie() throws JsonProcessingException {
        String cookiesStr = buyerAccount.getLoginCookie();
        if (cookiesStr != null && !cookiesStr.equals("")) {
            return new ObjectMapper().readValue(cookiesStr, MyCookie[].class);
        }
        return new MyCookie[]{};
    }


    public void closeAdPop() {
        try {
            Thread.sleep(2000L);
            var closeBtn = driverEngine.getExecutor().getByClassName(classSelector.homePageAdCloseButton);
            closeBtn.click();
            Thread.sleep(1000L);
        } catch (Exception ex) {
            System.out.println("No green ad pop");
            try {
                var closeBtn = driverEngine.getExecutor().getByXpath("//div[text()='Claim Now']");
                closeBtn.click();
                Thread.sleep(1000L);
            } catch (Exception ex2) {
                System.out.println("No claim now pop");
            }
        }
    }

    public void byebye() {
    }

    public void assemble() {
        resterEngine.plugIn(this);
        loginEngine.plugIn(this);
        stockEngine.plugIn(this);
        trackEngine.plugIn(this);
        fetcherEngine.plugIn(this);
        addressEngine.plugIn(this);
    }

    /**
     * @param buyerAccount
     */
    public void init(BuyerAccount buyerAccount) throws MalformedURLException, JsonProcessingException {
        this.buyerAccount = buyerAccount;
        this.buyerAccountService = SpringUtil.getBean(BuyerAccountService.class);
        this.driverEngine = new GridDriverEngine();
        assemble();
        initialBotCookie();
    }

    public abstract void solveLoginCaptcha() throws InterruptedException;

    public abstract void solvePayCaptcha() throws InterruptedException;
}
