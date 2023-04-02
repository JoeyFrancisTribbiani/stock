package com.yy.stock.test;

import com.yy.stock.bot.engine.driver.GridDriverEngine;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.util.ArrayList;

class WebDriverTest {

    @Test
    void testChromeDiver() throws InterruptedException, MalformedURLException {
        var dList = new ArrayList<WebDriver>();
        for (int i = 0; i < 2; i++) {
            var driverManager = new GridDriverEngine();
            var driver = driverManager.getDriver();
            dList.add(driver);
            driver.get("https://www.baidu.com");
            Thread.sleep(1000L);
        }
        System.out.println("开始循环打印title");
        for (int j = 0; j < 60; j++) {
            for (int i = 0; i < 2; i++) {
                var title = dList.get(i).getTitle();
                System.out.println(title);
            }
            Thread.sleep(20000L);
        }
        for (int i = 0; i < 2; i++) {
            dList.get(i).quit();
            Thread.sleep(5000L);
        }
    }

}