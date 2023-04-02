package com.yy.stock.bot.engine.driver;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@Slf4j
@Component("driverEngine")
public class GridDriverEngine {
    private CdpRemoteWebDriver driver;
    private InstructionExecutor executor;
    @Value("${bot.gridhub.url}")
    private String hubUrl;

    public GridDriverEngine() throws MalformedURLException {
        this.driver = initChromeDriver();
    }

    public void setOriginCookie(MyCookie[] originCookie) {
        addCookie(originCookie);
    }

    public MyCookie[] getCookie() {
        var cookies = driver.manage().getCookies();
        List<MyCookie> list = new ArrayList<>();
        MyCookie[] res = new MyCookie[]{};
        for (var ck : cookies) {
            MyCookie c = new MyCookie();
            BeanUtils.copyProperties(ck, c);
            list.add(c);
        }
        return list.toArray(res);
    }

    public void addCookie(MyCookie[] cookie) {
        for (var myCookie : cookie) {
            var c = new Cookie(myCookie.name, myCookie.value);
            driver.manage().addCookie(c);
        }
    }

    public void deleteCookie(MyCookie[] cookie) {
        for (var myCookie : cookie) {
            var c = new Cookie(myCookie.name, myCookie.value);
            driver.manage().deleteCookie(c);
        }
    }

    private boolean testSessionsFull() {
        return this.driver.getSessionId() == null;
    }

    public CdpRemoteWebDriver getDriver() {
        if (!testSessionsFull()) {
            try {
                this.driver = initChromeDriver();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return this.driver;
    }

    public InstructionExecutor getExecutor() {
        if (executor == null) {
            executor = new InstructionExecutor(this.driver);
        }
        return executor;
    }

    public void quitDriver() {
        if (this.driver != null) {
            this.driver.close();
            this.driver.quit();
        }
    }

    private CdpRemoteWebDriver initChromeDriver() throws MalformedURLException {
        log.info("开始初始化remoteChromeDriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");
        options.addArguments("--disable-popup-blocking"); // 禁用阻止弹出窗口
        options.addArguments("no-sandbox");//禁用沙盒
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--remote-allow-origins=*");

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
//        CdpRemoteWebDriver driver = new RemoteCdpRemoteWebDriver(new URL(hubUrl), chromeOptions);
        CdpRemoteWebDriver driver = new CdpRemoteWebDriver(new URL("http://115.159.45.30:26666"), chromeOptions);

        //修改window.navigator.webdirver=undefined，防机器人识别机制
        Map<String, Object> command = new HashMap<>();
        command.put("source", "Object.defineProperty(navigator, 'CdpRemoteWebDriver', {get: () => undefined})");
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", command);

        return driver;
    }

}