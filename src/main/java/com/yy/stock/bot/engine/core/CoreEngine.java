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
import com.yy.stock.bot.engine.register.RegisterEngine;
import com.yy.stock.bot.engine.rester.ResterEngine;
import com.yy.stock.bot.engine.stocker.AddressEngine;
import com.yy.stock.bot.engine.stocker.StockEngine;
import com.yy.stock.bot.engine.tracker.TrackEngine;
import com.yy.stock.bot.selector.BaseClassSelector;
import com.yy.stock.bot.selector.BaseUrls;
import com.yy.stock.bot.selector.BaseXpaths;
import com.yy.stock.dto.SkuModuleBase;
import com.yy.stock.dto.StockRequest;
import com.yy.stock.dto.TrackRequest;
import com.yy.stock.entity.BuyerAccount;
import com.yy.stock.entity.EmailAccount;
import com.yy.stock.service.BuyerAccountService;
import com.yy.stock.service.StockStatusService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.mail.MessagingException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Currency;
import java.util.Locale;

@Getter
@Slf4j
public abstract class CoreEngine {
    public BaseUrls urls;
    public BaseXpaths xpaths;
    public BaseClassSelector classSelector;
    protected GridDriverEngine driverEngine;
    protected ResterEngine resterEngine;
    protected LoginEngine loginEngine;
    protected RegisterEngine registerEngine;
    protected EmailEngine emailEngine;
    protected StockEngine stockEngine;
    protected AddressEngine addressEngine;
    protected TrackEngine trackEngine;
    protected FetcherEngine fetcherEngine;
    protected BuyerAccount buyerAccount;
    protected BuyerAccountService buyerAccountService;
    protected StockStatusService stockStatusService;
    protected BotStatus botStatus = BotStatus.idle;

    // bot状态
    // region
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

    public void updateLoginTime() {
        buyerAccount.setLastLoginTime(DateTime.now());
        buyerAccountService.save(buyerAccount);
    }

    //endregion


    public void init(BuyerAccount buyerAccount) throws MalformedURLException, JsonProcessingException {
        this.buyerAccount = buyerAccount;
        this.buyerAccountService = SpringUtil.getBean(BuyerAccountService.class);
        this.stockStatusService = SpringUtil.getBean(StockStatusService.class);
        this.driverEngine = new GridDriverEngine();
        assemble();
        initialBotCookieAndHeaders();
    }

    // bot header & cookie
    // region
    public void initialBotCookieAndHeaders() throws JsonProcessingException {
        var cookies = getLoginCookie();
        testGoHome();
        driverEngine.setCookie(cookies);
        testGoHome();
        resterEngine.updateCookie(cookies);
    }

    private MyCookie[] getLoginCookie() throws JsonProcessingException {
        String cookieStr = buyerAccount.getLoginCookie();
        if (cookieStr != null && !cookieStr.equals("")) {
            return new ObjectMapper().readValue(cookieStr, MyCookie[].class);
        }
        return new MyCookie[]{};
    }

    private HttpHeaders getLoginHeaders() throws JsonProcessingException {
        String headersStr = buyerAccount.getLoginCookie();
        if (headersStr != null && !headersStr.equals("")) {
            return new ObjectMapper().readValue(headersStr, HttpHeaders.class);
        }
        return new HttpHeaders();
    }

    public void updateBotCookie() throws JsonProcessingException {
//        var headers = driverEngine.getHeaders(urls.homePage);
        var cookie = driverEngine.getCookie();
        resterEngine.updateCookie(cookie);
        persistBotCookie(cookie);
    }

    public void persistBotCookie(MyCookie[] cookies) throws JsonProcessingException {
        var cookiesStr = new ObjectMapper().writeValueAsString(cookies);
        buyerAccount.setLoginCookie(cookiesStr);
        buyerAccountService.save(buyerAccount);
    }
//    public void persistBotHeaders(HttpHeaders headers) throws JsonProcessingException {
//        var cookiesStr = new ObjectMapper().writeValueAsString(headers);
//        buyerAccount.setLoginCookie(cookiesStr);
//        buyerAccountService.save(buyerAccount);
//    }
    //endregion

    public void goHome() {
        driverEngine.getDriver().get(urls.homePage);
        closeAdPop();
    }

    public void testGoHome() {
        driverEngine.getDriver().get(urls.homePage);
    }

    public BuyerAccount getBuyerAccount() {
        return buyerAccount;
    }

    public String getBuyerInfo() {
        return buyerAccount.getPlatform().getName() + " " + buyerAccount.getEmail() + " " + buyerAccount.getNickname();
    }

    public void register(EmailAccount emailAccount) throws MessagingException, IOException, InterruptedException {
        registerEngine.register(emailAccount);
    }

    public void login() throws InterruptedException, IOException, MessagingException {
        setBotStatus(BotStatus.logining);
        loginEngine.login();
        setBotStatus(BotStatus.idle);
    }

    public void stock(StockRequest stockRequest) throws InterruptedException, IOException, MessagingException {
        setBotStatus(BotStatus.stocking);

        var stockStatus = stockRequest.getStockStatus();
        stockStatus.setLastStockTryTime(DateTime.now().toLocalDateTime());
        stockStatusService.save(stockStatus);

        stockEngine.stock(stockRequest);

        buyerAccount.setOrderCount(buyerAccount.getOrderCount() + 1);
        buyerAccountService.save(buyerAccount);
        setBotStatus(BotStatus.idle);
    }

    public void track(TrackRequest trackRequest) throws InterruptedException, DatatypeConfigurationException, ParserConfigurationException, IOException {
        setBotStatus(BotStatus.tracking);
        trackEngine.track(trackRequest);
        setBotStatus(BotStatus.idle);
    }

    public SkuModuleBase fetch(String url) throws InterruptedException, MessagingException, IOException {
        setBotStatus(BotStatus.fetching);
        var html = fetcherEngine.fetch(url);
        setBotStatus(BotStatus.idle);
        return html;
    }

    public String getCountryCode() {
        var platform = getBuyerAccount().getPlatform();
        return platform.getCountryCode();
    }

    public String locateCountryName() {
        var platform = getBuyerAccount().getPlatform();
        var code = platform.getCountryCode();
        return new Locale("en", code).getDisplayCountry(new Locale("en", code));
    }

    public String locateCountryCurrency() {
        var platform = getBuyerAccount().getPlatform();
        var code = platform.getCountryCode();
//        return Currency.getInstance(code).getDisplayName(new Locale("en", code));
        return Currency.getInstance(new Locale("en", code)).getCurrencyCode();
    }

    public BigDecimal parseUSDMoney(String text) {
        if (text == null || text.equals("") || text.toLowerCase().equals("free"))
            return new BigDecimal(0);
        text = text.substring(4);
        text = text.replace(',', '.');
        return new BigDecimal(text);
    }

    public BigDecimal parseMoney(String text) {
        return parseUSDMoney(text);
    }


    public void closeAdPop() {
        log.debug("开始关闭广告弹窗 ...");
        try {
            Thread.sleep(2000L);
            var closeBtn = driverEngine.getExecutor().getByClassName(classSelector.homePageAdCloseButton);
            closeBtn.click();
            Thread.sleep(1000L);
        } catch (Exception ex) {
            log.debug("首页弹窗检测：未找到广告弹窗.");
            try {
                var closeBtn = driverEngine.getExecutor().getByXpath("//div[text()='Claim Now']");
                closeBtn.click();
                Thread.sleep(1000L);
            } catch (Exception ex2) {
                log.debug("首页弹窗检测：未找到折扣弹窗.");
            }
        }
    }

    public void byebye() {
        try {
            driverEngine.byebye();
        } catch (Exception ex) {
            log.info("Driver already closed, byebye");
        }
    }

    public void assemble() {
        resterEngine.plugIn(this);
        loginEngine.plugIn(this);
        stockEngine.plugIn(this);
        trackEngine.plugIn(this);
        fetcherEngine.plugIn(this);
        addressEngine.plugIn(this);
        resterEngine.plugIn(this);
    }

    public abstract void solveLoginCaptcha() throws InterruptedException;

    public abstract void solvePayCaptcha() throws InterruptedException;
}
