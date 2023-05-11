package com.yy.stock.bot.engine.driver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.stock.bot.engine.driver.model.ChromePerformanceLogRequestWillBeSent.ChromePerformanceLogRequestWillBeSent;
import com.yy.stock.bot.engine.driver.model.ChromePerformanceLogRequestWillBeSentExtraInfo.ChromePerformanceLogRequestWillBeSentExtraInfo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class DebugChromeDriverEngine {
    public DebugInstructionExecutor executor;
    private ChromeDriver driver;

    public DebugChromeDriverEngine() {
        this.driver = initChromeDriver();
        executor = new DebugInstructionExecutor(driver);
    }


    public HttpHeaders getHeaders(String requestUrl) throws JsonProcessingException {
        var logs = driver.manage().logs().get(LogType.PERFORMANCE);
        var requestId = "";
        for (var log : logs) {
            var message = log.getMessage();
            if (message.contains("Network.requestWillBeSent") && message.contains(requestUrl)) {
                System.out.println(message);
                try {
                    ChromePerformanceLogRequestWillBeSent model = new ObjectMapper().readValue(message, ChromePerformanceLogRequestWillBeSent.class);
                    if (model.getMessage().getParams().getRequest().getUrl().contains(requestUrl)) {
                        requestId = model.getMessage().getParams().getRequestId();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (var log : logs) {
            var message = log.getMessage();
            if (message.contains("Network.requestWillBeSentExtraInfo")) {
                System.out.println(message);
                try {
                    ChromePerformanceLogRequestWillBeSentExtraInfo model = new ObjectMapper().readValue(message, ChromePerformanceLogRequestWillBeSentExtraInfo.class);
                    if (model.getMessage().getParams().getRequestId().equals(requestId)) {
                        var h = model.getMessage().getParams().getHeaders();
                        var headers = new HttpHeaders();
                        for (var key : h.keySet()) {
                            headers.add(key, h.get(key));
                        }
                        return headers;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void clearDriver() {
        this.driver = null;
    }

    public ChromeDriver getDriver() {
        if (this.driver == null) {
            log.info("第一次获取driver -- driver == null: " + (this.driver == null));
            this.driver = initChromeDriver();
        }
        return this.driver;
    }

    public void quitDriver() {
        if (this.driver != null) {
//            this._driver = null;
//            this._driver.quit();
        }
    }

    private ChromeDriver initChromeDriver() {
        log.info("开始初始化chromeDriver");
//        String driverPath = "/Users/minmin/Documents/Fadacai88888/stock/libs/chromedriver";

        String driverPath = "D:\\Fadacai888888\\wimoor_work\\chromedriver_113_win.exe";
        System.setProperty("webdriver.chrome.driver",
                driverPath);
        System.setProperty("webdriver.http.factory", "jdk-http-client");

        ChromeOptions options = new ChromeOptions();

//        LoggingPreferences logPrefs = new LoggingPreferences();
//        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
//        logPrefs.enable(LogType.BROWSER, Level.ALL);
//
//        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

        options.addArguments("--remote-allow-origins=*");

        options.setExperimentalOption("debuggerAddress", "127.0.0.1:8888");
//        options.setExperimentalOption("w3c", false);


        ChromeDriver driver = new ChromeDriver(options);
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