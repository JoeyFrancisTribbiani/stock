package com.yy.stock.test;

import com.yy.stock.adaptor.seleniumgrid.model.GridStatusResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;

class WebDriverTest {

    @Test
    void testChromeDiver() throws InterruptedException, MalformedURLException {
//        var dList = new ArrayList<WebDriver>();
//        for (int i = 0; i < 2; i++) {
//            var driverManager = new GridDriverEngine();
//            var driver = driverManager.getDriver();
//            dList.add(driver);
//            driver.get("https://www.baidu.com");
//            Thread.sleep(1000L);
//        }
//        System.out.println("开始循环打印title");
//        for (int j = 0; j < 60; j++) {
//            for (int i = 0; i < 2; i++) {
//                var title = dList.get(i).getTitle();
//                System.out.println(title);
//            }
//            Thread.sleep(20000L);
//        }
//        for (int i = 0; i < 2; i++) {
//            dList.get(i).quit();
//            Thread.sleep(5000L);
//        }
    }

    @Test
    void testGridStatus() {
        var restTemplate = new RestTemplate();
        HttpEntity<GridStatusResponseModel> response = restTemplate.exchange("http://115.159.45.30:26666/status", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        var gridStatusResponseModel = response.getBody();
        var countSlots = gridStatusResponseModel.getValue().getNodes().stream().mapToInt(node -> node.getSlots().size()).sum();
        var countNotNullSessionsThatSessionInSlots = gridStatusResponseModel.getValue().getNodes().stream().mapToInt(node -> (int) node.getSlots().stream().filter(slot -> slot.getSession() != null).count()).sum();
        System.out.println(response.getBody());
    }
}