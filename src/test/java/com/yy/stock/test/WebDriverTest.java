package com.yy.stock.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yy.stock.adaptor.seleniumgrid.model.GridStatusResponseModel;
import com.yy.stock.bot.engine.driver.DebugChromeDriverEngine;
import com.yy.stock.scheduler.SelectAmazonProductScheduler;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;

class WebDriverTest {

    @Test
    void testSelection(){
        var selectionScheduler = new SelectAmazonProductScheduler();
        selectionScheduler.driverEngine=new DebugChromeDriverEngine();
        var searchUrl  ="https://www.amazon.sg/gp/bestsellers/electronics/8417576051?ref_=Oct_d_obs_S&pd_rd_w=L3RlM&content-id=amzn1.sym.878cff72-f123-4f0b-b0b8-51da456a0c3e&pf_rd_p=878cff72-f123-4f0b-b0b8-51da456a0c3e&pf_rd_r=787JMGGGMW5QZVR1B6P3&pd_rd_wg=EAMie&pd_rd_r=290bfb78-c142-42b3-a6b5-4188c9ca4850";
        var searchKey = "Micro SD";
        selectionScheduler.fetchAsin(searchUrl,searchKey);
    }
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

    public int getCusorWidth() {
        return 16;
    }

    @Test
    public void solveChickenCaptcha() throws InterruptedException, JsonProcessingException {
        var driverEngine = new DebugChromeDriverEngine();
        var ulr = "https://passport.aliexpress.com/";
        var testUrl = "https://passport.aliexpress.com/ac/pc_r_open.htm?fromSite=13";
//        if (!driverEngine.getExecutor().getCurrentUrl().contains(ulr)) {
//            return;
//        }
//        Thread.sleep(5000);
//        driverEngine.getExecutor().switchToFrame("baxia-dialog-content");
        driverEngine.getDriver().get(testUrl);
        Thread.sleep(5000);
        var headers = driverEngine.getHeaders("passport.aliexpress.com");

        JavascriptExecutor executor = driverEngine.getDriver();
        executor.executeScript(listenMouseMove(), "");
        var chickenIconsClassName = "nc-scrape-icon";
        var chickenIcons = driverEngine.getExecutor().listByClassName(chickenIconsClassName);
        var bgCanvas = driverEngine.getExecutor().getById("nc_1_canvas");
        var chickenIcon = chickenIcons.get(0);
        var chickenIconHeight = chickenIcon.getSize().getHeight();
        var chickenIconX = chickenIcon.getLocation().getX();
        var chickenIconY = chickenIcon.getLocation().getY();
        var xs = new int[]{chickenIconX + 8, 0, getCusorWidth(), 0, getCusorWidth(), 0, getCusorWidth(), 0, getCusorWidth(), 0};
        var ys = new int[]{chickenIconY, chickenIconHeight, 0, -chickenIconHeight, 0, chickenIconHeight, 0, -chickenIconHeight, 0, chickenIconHeight};
        driverEngine.getExecutor().moveToAndDropAndDropBy(xs, ys);
        Thread.sleep(1000);


        chickenIcon = chickenIcons.get(1);
        chickenIconHeight = chickenIcon.getSize().getHeight();
        chickenIconX = chickenIcon.getLocation().getX() - chickenIconX - (4 * getCusorWidth());
        chickenIconY = chickenIcon.getLocation().getY() - chickenIconY - chickenIconHeight;
        xs = new int[]{chickenIconX - 8, 0, getCusorWidth(), 0, getCusorWidth(), 0, getCusorWidth(), 0, getCusorWidth(), 0, -8 * getCusorWidth()};
        ys = new int[]{chickenIconY - 6, chickenIconHeight, 0, -chickenIconHeight, 0, chickenIconHeight, 0, -chickenIconHeight, 0, chickenIconHeight, -chickenIconHeight};
        driverEngine.getExecutor().moveToAndDropAndDropBy(xs, ys);
        Thread.sleep(3000);
        var submitButton = driverEngine.getExecutor().getById("submitBtn");
        submitButton.click();
        Thread.sleep(1000);
    }

    private String listenMouseMove() {
        return """
                document.addEventListener('mousemove', function (e) {
                    console.log(e.clientX, e.clientY);
                });
                """;
    }

    private String moveCusor(int x1, int y1, int x2, int y2) {
        return """
                var event = new MouseEvent('mousemove', {
                  clientX: 582,
                  clientY: 359
                });
                document.dispatchEvent(event);                
                console.log("move to 12,16");
                var event = new MouseEvent('mousemove', {
                  clientX: 562,
                  clientY:380 
                });
                document.dispatchEvent(event);                
                console.log("move to 22,26");
                """;
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