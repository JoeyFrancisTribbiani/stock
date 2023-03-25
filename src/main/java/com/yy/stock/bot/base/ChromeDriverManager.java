package com.yy.stock.bot.base;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@Slf4j
public class ChromeDriverManager {
    private ChromeDriver _driver;

    public ChromeDriver getDriver() {
        if (this._driver == null) {
            log.info("第一次获取driver -- driver == null: " + (this._driver == null));
            this._driver = initChromeDriver();
        }
        return this._driver;
    }

    public void quitDriver() {
        if (this._driver != null) {
            this._driver.quit();
        }
    }

    private ChromeDriver initChromeDriver() {
        log.info("开始初始化chromeDriver");
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
        log.info("初始化chromeDriver完成");
        return driver;
    }


    public MyCookie[] getDriverCookies() {
        var cookies = getDriver().manage().getCookies();
        List<MyCookie> list = new ArrayList<>();
        MyCookie[] res = new MyCookie[]{};
        for (var ck : cookies) {
            MyCookie c = new MyCookie();
            BeanUtils.copyProperties(ck, c);
            list.add(c);
        }
        return list.toArray(res);
    }
}