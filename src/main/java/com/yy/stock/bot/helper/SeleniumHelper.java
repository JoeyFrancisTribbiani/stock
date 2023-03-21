package com.yy.stock.bot.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.HttpHeaders;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.Duration;
import java.util.BitSet;
import java.util.List;

public class SeleniumHelper {
    public static int WAIT_SECONDS = 8;

    public static void clearAndType(WebElement field, String text) {
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

//    public static void clearAndType(ChromeDriver driver, WebElement field, String text) {
//        clearJs(field, driver);
//        field.sendKeys(text);
//    }
//
//    public static void clearJs(WebElement ele, ChromeDriver driver) {
//        driver.executeScript("arguments[0].value = '';", ele);
//    }

    public static boolean isNumberString(String str) {
        if (str == null) return false;
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public static WebElement getByXpath(ChromeDriver driver, String xpath) {
        return new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(d -> d.findElement(By.xpath(xpath)));
    }

    public static WebElement getByRelativeXpath(ChromeDriver driver, WebElement root, String xpath) {
        return new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(d -> root.findElement(By.xpath(xpath)));
    }

    public static List<WebElement> listByRelativeXpath(ChromeDriver driver, WebElement root, String xpath) {
        return new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(d -> root.findElements(By.xpath(xpath)));
    }

    public static List<WebElement> listByXpath(ChromeDriver driver, String xpath) {
        return new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(d -> d.findElements(By.xpath(xpath)));
    }

    public static WebElement getByClassName(ChromeDriver driver, String className) {
        return new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(d -> d.findElement(By.className(className)));
    }

    public static List<WebElement> listByClassName(ChromeDriver driver, String className) {
        return new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(d -> d.findElements(By.className(className)));
    }

    public static List<WebElement> listByTagName(ChromeDriver driver, String tag) {
        return new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(d -> d.findElements(By.tagName(tag)));
    }

    public static void updateHeaderValueFromLogs(ChromeDriver driver, HttpHeaders toUpdate, String[] headerNames) {
        List<LogEntry> logs = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
//        Byte[] flags = new Byte[headerNames.length];
        BitSet flags = new BitSet(headerNames.length);

        for (LogEntry log : logs) {
            if (flags.cardinality() == flags.length()) {
                break;
            }
            String message = log.getMessage();
            for (int i = 0; i < headerNames.length; i++) {
                if (flags.get(i) == false) {
                    String v = MessageHelper.getValueFromJsonMessage(message, headerNames[i]);
                    if (v != null) {
                        toUpdate.set(headerNames[i], v);
                        flags.set(i);
                    }
                }
            }
        }
    }

    public static HttpHeaders parseFiddlerSavedHeaders(String headerString) {
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
}
