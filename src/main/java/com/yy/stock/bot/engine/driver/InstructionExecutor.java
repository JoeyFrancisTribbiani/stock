package com.yy.stock.bot.engine.driver;

import com.yy.stock.bot.helper.MessageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.HttpHeaders;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.Duration;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

@Slf4j
public class InstructionExecutor {
    private final CdpRemoteWebDriver driver;
    int WAIT_SECONDS = 8;

    public InstructionExecutor(CdpRemoteWebDriver driver) {
        this.driver = driver;
    }


    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageSource() {
        return driver.getPageSource();
    }


    public String getBrowserInfo() {
        Capabilities cap = driver.getCapabilities();

        String browserName = cap.getBrowserName();
        String browserVersion = (String) cap.getCapability("browserVersion");
        String osName = cap.getCapability("platformName").toString().toLowerCase();

        return browserName + browserVersion + "-" + osName;
    }

    public void clearAndType(WebElement field, String text) throws InterruptedException {
        field.clear();
        field.click();
        if (!field.getAttribute("value").equals("")) {
            var os = getBrowserInfo();
            if (os != null) {
                log.info("浏览器代理操作系统信息: {}", os);
                os = os.toLowerCase();
                if (os.contains("mac")) {
                    field.sendKeys(Keys.COMMAND, "a");
                    log.info("发送按键内容: {}", "Keys.COMMAND + a");
                } else {
                    field.sendKeys(Keys.CONTROL, "a");
                    log.info("发送按键内容: {}", "Keys.CONTROL + a");
                }
            } else {
                field.sendKeys(Keys.CONTROL, "a");
            }
            Thread.sleep(2000);
            field.sendKeys(Keys.BACK_SPACE);
            Thread.sleep(2000);
            log.info("发送按键内容: {}", "Keys.BACK_SPACE");
        }
        field.click();
        field.clear();
        field.click();
        field.sendKeys(text);
    }

    public void waitForUrl(String url, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.urlContains(url));
    }
//        new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS))
//                .until(d -> d.getCurrentUrl().contains(url));

    public boolean isNumberString(String str) {
        if (str == null) return false;
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public WebElement getByXpath(String xpath) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS))
                .until(d -> d.findElement(By.xpath(xpath)));
    }

    public WebElement getByRelativeXpath(WebElement root, String xpath) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS))
                .until(d -> root.findElement(By.xpath(xpath)));
    }

    public List<WebElement> listByRelativeXpath(WebElement root, String xpath) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS))
                .until(d -> root.findElements(By.xpath(xpath)));
    }

    public List<WebElement> listByXpath(String xpath) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS))
                .until(d -> d.findElements(By.xpath(xpath)));
    }

    public WebElement getByClassName(String className) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS))
                .until(d -> d.findElement(By.className(className)));
    }

    public WebElement getByClassName(WebElement el, String className) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS))
                .until(d -> el.findElement(By.className(className)));
    }

    public WebElement getByTagName(WebElement el, String tagName) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS))
                .until(d -> el.findElement(By.tagName(tagName)));
    }

    public WebElement getByTagName(String tagName) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS))
                .until(d -> d.findElement(By.tagName(tagName)));
    }

    public List<WebElement> listByTagName(WebElement el, String tagName) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS))
                .until(d -> el.findElements(By.tagName(tagName)));
    }

    public List<WebElement> listByClassName(String className) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS))
                .until(d -> d.findElements(By.className(className)));
    }

    public List<WebElement> listByTagName(String tag) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS))
                .until(d -> d.findElements(By.tagName(tag)));
    }

    public void updateHeaderValueFromLogs(HttpHeaders toUpdate, String[] headerNames) {
        List<LogEntry> logs = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
//        Byte[] flags = new Byte[headerNames.length];
        BitSet flags = new BitSet(headerNames.length);

        for (LogEntry log : logs) {
            if (flags.cardinality() == flags.length()) {
                break;
            }
            String message = log.getMessage();
            for (int i = 0; i < headerNames.length; i++) {
                if (!flags.get(i)) {
                    String v = MessageHelper.getValueFromJsonMessage(message, headerNames[i]);
                    if (v != null) {
                        toUpdate.set(headerNames[i], v);
                        flags.set(i);
                    }
                }
            }
        }
    }

    public HttpHeaders parseFiddlerSavedHeaders(String headerString) {
        String[] lines = headerString.split("\n");
        HttpHeaders headers = new HttpHeaders();
        for (String line : lines) {
            String[] p = line.split("; Value: ");
            String name = p[0].substring(5);
            String value = p[1];
            headers.set(name, value);
        }
        return headers;
    }

    public WebElement getById(String s) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS)).until(d -> d.findElement(By.id(s)));
    }

    public void dragAndDropBy(WebElement capButton, int[] xs, int[] ys) throws InterruptedException {
        Actions action = new Actions(driver);
//        action.dragAndDropBy(capButton, slidingDistance, i).build().perform();
        action.clickAndHold(capButton);
        for (int j = 0; j < xs.length; j++) {
            action.moveByOffset(xs[j], ys[j]);
            Thread.sleep(RandomUtils.nextInt(100, 500));
        }
        action.release().build().perform();
    }

    public void moveToAndDropAndDropBy(WebElement backCanvas, int[] xs, int[] ys) throws InterruptedException {
        Actions action = new Actions(driver);
//        action.dragAndDropBy(capButton, slidingDistance, i).build().perform();
        action.moveToElement(backCanvas);
        Thread.sleep(1000);
        action.moveByOffset(xs[0], ys[0]);
        Thread.sleep(1000);
        action.clickAndHold();
        for (int j = 1; j < xs.length; j++) {
            action.moveByOffset(xs[j], ys[j]);
            Thread.sleep(RandomUtils.nextInt(100, 500));
        }
        action.release().build().perform();
    }

    public void switchToFrame(String s) {
        driver.switchTo().frame(s);
    }

    public void switchToFrame(int i) {
        driver.switchTo().frame(i);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void moveToAndDropAndDropBy(int[] xs, int[] ys) throws InterruptedException {
        Actions action = new Actions(driver);
        action.moveByOffset(xs[0], ys[0]).perform();
        action.click().perform();
        Thread.sleep(1000);
        action.clickAndHold().perform();
        for (int j = 1; j < xs.length; j++) {
            var pointsX = getTrack(xs[j]);
            var pointsY = getTrack(ys[j]);
            if (pointsX.size() != pointsY.size()) {
                if (pointsX.size() > pointsY.size()) {
                    pointsY = new ArrayList<>(pointsX.size());
                    for (int i = 0; i < pointsX.size(); i++) {
                        pointsY.add(0);
                    }
                } else {
                    pointsX = new ArrayList<>(pointsY.size());
                    for (int i = 0; i < pointsY.size(); i++) {
                        pointsX.add(0);
                    }
                }
            }
            for (int k = 0; k < pointsX.size(); k++) {
                action.moveByOffset(pointsX.get(k), pointsY.get(k)).perform();
            }
        }
    }

    public List<Integer> getTrack(int distance) {
        // 移动轨迹
        List<Integer> track = new ArrayList<>();
        // 当前位移
        int current = 0;
        // 减速阈值
        int mid = Math.abs(distance) * 4 / 5;
        // 计算间隔
        double t = 0.5;
        // 初速度
        double v = 1;


        //distance 求绝对值
        while (current < Math.abs(distance)) {
            int a;
            if (current < mid) {
                // 加速度为2
                a = 4;
            } else {
                // 加速度为-2
                a = -3;
            }
            double v0 = v;
            // 当前速度
            v = v0 + a * t;
            // 移动距离
            int move = (int) Math.round(v0 * t + 1.0 / 2 * a * t * t);
            // 当前位移
            current += move;
            // 加入轨迹
            if (distance < 0) {
                track.add(-move);
            } else {
                track.add(move);
            }
//            track.add(move);
        }
        return track;
    }

    public WebElement getByCssSelector(String className) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS))
                .until(d -> d.findElement(By.cssSelector(className)));
    }
    public List<WebElement> listByCssSelector(String className) {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_SECONDS))
                .until(d -> d.findElements(By.cssSelector(className)));
    }

}
