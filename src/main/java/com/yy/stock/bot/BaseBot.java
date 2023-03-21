package com.yy.stock.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.yy.stock.bot.base.Product;
import com.yy.stock.bot.base.ShipmentInfo;
import com.yy.stock.dto.StockRequest;
import com.yy.stock.entity.BuyerAccount;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class BaseBot implements Bot {

    private ChromeDriver _driver;

    protected ChromeDriver getDriver() {
        if (this._driver == null) {
            this._driver = initChromeDriver();
        }
        return this._driver;
    }

    private ChromeDriver initChromeDriver() {
        System.setProperty("webdriver.chrome.driver",
                "/Users/minmin/Documents/Fadacai88888/stock/libs/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        options.addArguments("--disable-popup-blocking"); // 禁用阻止弹出窗口
        options.addArguments("no-sandbox");//禁用沙盒
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("disable-extensions"); // 禁用扩展
        options.addArguments("no-default-browser-check"); // 默认浏览器检查

        List<String> excludeSwitches = Lists.newArrayList("enable-automation");//设置ExperimentalOption
        options.setExperimentalOption("excludeSwitches", excludeSwitches);
//        options.setExperimentalOption("useAutomationExtension", false);
        Map<String, Object> prefs = new HashMap();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);// 禁用保存密码提示框

        // set performance logger
        // this sends Network.enable to chromedriver
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

        ChromeDriver driver = new ChromeDriver(options);

        //修改window.navigator.webdirver=undefined，防机器人识别机制
        Map<String, Object> command = new HashMap<>();
        command.put("source", "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", command);
        return driver;
    }

    /**
     * @return
     */
    @Override
    public String getBotName() {
        return null;
    }

    /**
     * @param requestDTO
     * @return
     * @throws InterruptedException
     * @throws JsonProcessingException
     */
    @Override
    public boolean doStock(StockRequest requestDTO) throws InterruptedException, JsonProcessingException {
        return false;
    }

    /**
     * @param orderId
     * @return
     */
    @Override
    public ShipmentInfo trackOrder(String orderId) {
        return null;
    }

    /**
     * @param product
     * @return
     */
    @Override
    public boolean returnOrder(Product product) {
        return false;
    }

    /**
     * @param buyerAccount
     */
    @Override
    public void setBuyerAccount(BuyerAccount buyerAccount) {

    }

    /**
     * @param url
     * @return
     * @throws IOException
     */
    @Override
    public String getProductHtmlSource(String url) throws IOException, InterruptedException {
        return null;
    }

    /**
     * @param html
     * @return
     */
    @Override
    public String getSkuProperties(String html) {
        return null;
    }

    /**
     *
     */
    @Override
    public void quitDriver() {
        if (this._driver != null) {
            this._driver.quit();
        }
    }

    protected BigDecimal parseUSDMoney(String text) {
        text = text.substring(4);
        text = text.replace(',', '.');
        return new BigDecimal(text);
    }

}
