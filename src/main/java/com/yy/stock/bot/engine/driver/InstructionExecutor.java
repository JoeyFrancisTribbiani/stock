package com.yy.stock.bot.engine.driver;

import com.yy.stock.bot.helper.MessageHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.HttpHeaders;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.Duration;
import java.util.BitSet;
import java.util.List;

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

    public void clearAndType(WebElement field, String text) {
        field.clear();
        field.click();
        if (!field.getAttribute("value").equals("")) {
            var os = System.getProperty("os.name");
            if (os != null) {
                os = os.toLowerCase();
                if (os.contains("mac")) {
                    field.sendKeys(Keys.COMMAND, "a");
                } else {
                    field.sendKeys(Keys.CONTROL, "a");
                }
            } else {
                field.sendKeys(Keys.CONTROL, "a");
            }
            field.sendKeys(Keys.BACK_SPACE);
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
}
