package com.yy.stock.bot.engine.driver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yy.stock.bot.engine.driver.model.ChromePerformanceLogRequestWillBeSent.ChromePerformanceLogRequestWillBeSent;
import com.yy.stock.bot.engine.driver.model.ChromePerformanceLogRequestWillBeSentExtraInfo.ChromePerformanceLogRequestWillBeSentExtraInfo;
import com.yy.stock.common.util.MySpringUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public class GridHeadlessDriverEngine {
    private CdpRemoteWebDriver driver;
    private InstructionExecutor executor;

    private GridDriverEngineConfig gridDriverEngineConfig;

    public GridHeadlessDriverEngine()  {
        gridDriverEngineConfig = MySpringUtil.getBean(GridDriverEngineConfig.class);
        this.driver = initChromeDriver();
    }

    public CdpRemoteWebDriver getDriver() {
        return this.driver;
    }

    public HttpHeaders getHeadersDontWork(String requestUrl) throws JsonProcessingException {
        driver.get(requestUrl);
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

    public void setCookie(MyCookie[] cookie) {
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

    public String getHtml(String url) {
        driver.get(url);
        return driver.getPageSource();
    }

    public InstructionExecutor getExecutor() {
        if (executor == null) {
            executor = new InstructionExecutor(this.driver);
        }
        return executor;
    }

    public void byebye() {
        if (this.driver != null) {
            this.driver.close();
            this.driver.quit();
            this.driver = null;
            log.info("Driver now close, byebye");
        }
    }

    private CdpRemoteWebDriver initChromeDriver()  {
        log.info("开始初始化remoteChromeDriver");
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--headless");//无头浏览
        options.addArguments("blink-settings=imagesEnabled=false");//禁用图片

        options.addArguments("disable-infobars");
        options.addArguments("--disable-popup-blocking"); // 禁用阻止弹出窗口
        options.addArguments("no-sandbox");//禁用沙盒
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--remote-allow-origins=*");

        options.addArguments("disable-extensions"); // 禁用扩展
        options.addArguments("no-default-browser-check"); // 默认浏览器检查

        List<String> excludeSwitches = Lists.newArrayList("enable-automation");//设置ExperimentalOption
        options.setExperimentalOption("excludeSwitches", excludeSwitches);
        options.setExperimentalOption("useAutomationExtension", false);
        Map<String, Object> prefs = new HashMap();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);// 禁用保存密码提示框

        // set performance logger
        // this sends Network.enable to chromedriver


        //开启性能日志会导致内存占用不断增加
//        Map<String, Object> networkPrefs = new HashMap();
//        networkPrefs.put("enableNetwork", true);
//        networkPrefs.put("enablePage", false);
//        options.setExperimentalOption("perfLoggingPrefs", networkPrefs);
//        LoggingPreferences logPrefs = new LoggingPreferences();
//        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
//        options.setCapability("goog:loggingPrefs", logPrefs);
//        options.setCapability("goog:perfLoggingPrefs", networkPrefs);

        var registerUrl = gridDriverEngineConfig.getRegisterUrl();
        URL url =null;
        try {
            url = new URL(registerUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
        CdpRemoteWebDriver driver = new CdpRemoteWebDriver(url, options);
        driver.manage().window().maximize();

        Map<String, Object> command = new HashMap<>();
        command.put("source", "Object.defineProperty(navigator, 'CdpRemoteWebDriver', {get: () => undefined})");
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", command);

        return driver;
    }

    public String getBrowserInfo() {
        Capabilities cap = driver.getCapabilities();

        String browserName = cap.getBrowserName();
        String browserVersion = (String) cap.getCapability("browserVersion");
        String osName = Platform.fromString((String) cap.getCapability("platformName")).name().toLowerCase();

        return browserName + browserVersion + "-" + osName;
    }
}