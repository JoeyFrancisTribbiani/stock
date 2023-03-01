package com.yy.stock.bot.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.HttpHeaders;

import java.time.Duration;
import java.util.BitSet;
import java.util.List;

public class SeleniumHelper {
    public static int WAIT_SECONDS = 8;

    public static void clearAndType(WebElement field, String text) {
        field.clear();
        field.sendKeys(text);
    }

    public static WebElement getByXpath(ChromeDriver driver, String xpath) {
        return new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(d -> d.findElement(By.xpath(xpath)));
    }

    public static List<WebElement> getByClassName(ChromeDriver driver, String className) {
        return new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(d -> d.findElements(By.className(className)));
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
