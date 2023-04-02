package com.yy.stock.bot.aliexpressbot.engine.tracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.stock.bot.aliexpressbot.model.logistic.cainiao.CainiaoGlobalLogisticCityResponseModel;
import com.yy.stock.bot.aliexpressbot.model.logistic.cainiao.CainiaoGlobalLogisticDetailResponseModel;
import com.yy.stock.bot.aliexpressbot.model.logistic.cainiao.Module;
import com.yy.stock.bot.engine.core.CoreEngine;
import com.yy.stock.bot.engine.tracker.TrackEngine;
import com.yy.stock.config.StatusEnum;
import com.yy.stock.entity.StockStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class AliExpressTrackEngine extends TrackEngine {

    public AliExpressTrackEngine(CoreEngine coreEngine) {
        super(coreEngine);
    }

    @Override
    public void trackLogisticByStockStatus(StockStatus stockStatus) throws InterruptedException, DatatypeConfigurationException, ParserConfigurationException, IOException {
        var platformOrderId = stockStatus.getPlatformOrderId();
        if (platformOrderId == null || platformOrderId.equals("")) {
            stockStatus.setLog("状态信息中未保存平台订单ID!");
            stockStatus.setStatus(StatusEnum.payedButInfoSaveError.name());
            stockStatusService.save(stockStatus);
            log.error(coreEngine.getBotName() + "状态信息中未保存平台订单ID! ");
            return;
        }
        var trackNumber = stockStatus.getShipmentTrackNumber();
        if (trackNumber == null || trackNumber == "") {
            log.info(coreEngine.getBotName() + "状态信息中未保存订单追踪号");
            log.info(coreEngine.getBotName() + "开始去速卖通获取订单追踪号");
            boolean fetched = refetchShipmentTrackNumber(stockStatus);
            if (!fetched) {
                return;
            } else {
                // submit feed to amazon update order fulfillment
                log.info(coreEngine.getBotName() + "成功获取到订单追踪号, 开始提交feed给亚马逊更新订单物流信息" + stockStatus.getShipmentTrackNumber());
                orderFulfillmentSubmitFeedService.submit(stockStatus);
            }
        }

        log.info(coreEngine.getBotName() + "开始获取订单物流信息");
        trackNumber = stockStatus.getShipmentTrackNumber();
        var modules = getCainiaoTrackInfo(trackNumber);
        var module = modules.get(0);
        Thread.sleep(1000L);
        var city = getCainiaoCityInfo(trackNumber);
        module.setDestCity(city);
        var json = new ObjectMapper().writer().writeValueAsString(module);
        log.info(coreEngine.getBotName() + "获取到订单物流信息: " + json);
        stockStatus.setShipment(json);
        stockStatus.setLastShipmentTrackTime(LocalDateTime.now());
        stockStatusService.save(stockStatus);
    }

    public String getCainiaoCityInfo(String trackNumber) {
        var url = "https://global.cainiao.com/global/getCity.json?lang=en-US&language=en-US&mailNo=" + trackNumber;
        HttpEntity<String> entity = new HttpEntity<>(getCainiaoGlobalHeaders());

        try {
            HttpEntity<CainiaoGlobalLogisticCityResponseModel> response = resterEngine.getRestTemplate().exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
            });
            return response.getBody().getModule();
        } catch (Exception ex) {
            log.error(coreEngine.getBotName() + "拉取菜鸟物流追踪信息出错! trackUrl:" + url + "ex:" + ex.getMessage());
        }
        return "";
    }

    public List<Module> getCainiaoTrackInfo(String trackNumber) {
        var url = coreEngine.urls.cainiaoGlobalTrackApi + trackNumber;
        return getCainiaoTrackInfoByUrl(url);
    }

    public List<Module> getCainiaoTrackInfoByUrl(String url) {
        HttpEntity<String> entity = new HttpEntity<>(getCainiaoGlobalHeaders());

        try {
            HttpEntity<CainiaoGlobalLogisticDetailResponseModel> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
            });
            return response.getBody().getModule();
        } catch (Exception ex) {
            log.error(coreEngine.getBotName() + "拉取菜鸟物流追踪信息出错! trackUrl:" + url + "ex:" + ex.getMessage());
        }
        return null;
    }

    public boolean refetchShipmentTrackNumber(StockStatus stockStatus) throws JsonProcessingException, InterruptedException {
        String shipTrackNumber = null;

        try {
            if (!logined) {
//                if (!canLoginUseBuyerCookie()) {
                if (!login()) {
                    return false;
                }
//                }
            }
            getDriver().get("https://track.aliexpress.com/logisticsdetail.htm?tradeId=" + stockStatus.getPlatformOrderId());
            var noDiv = SeleniumHelper.getByClassName(getDriver(), "tracking-no");
            shipTrackNumber = noDiv.getText();

//            updateCookiesInHeaders(chromeDriverManager.getDriverCookies());
//
//            HttpEntity<String> entity = new HttpEntity<>(savedHeaders);
//            var url = urls.logisticDetailApi + stockStatus.getPlatformOrderId();
//            HttpEntity<LogisticDetailResponseModel> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
//            });
//            shipTrackNumber = Objects.requireNonNull(response.getBody()).getData().getPackages().get(0).getLogisticsNo();
            if (shipTrackNumber != null) {
                stockStatus.setShipmentTrackNumber(shipTrackNumber);
                stockStatus.setShipmentTrackUrl("https://global.cainiao.com/newDetail.htm?mailNoList=" + shipTrackNumber);
                stockStatus.setStatus(StatusEnum.shipped.name());
                stockStatusService.save(stockStatus);
                return true;
            }
        } catch (Exception ex) {
            stockStatus.setLog("拉取物流追踪号失败！ex:" + ex.getMessage());
            stockStatusService.save(stockStatus);
            log.info(coreEngine.getBotName() + "拉取物流追踪号失败! statusID:" + stockStatus.getId() + "ex:" + ex.getMessage());
            return false;
        } finally {
            quitDriver();
        }
        return false;
    }


    public String trackLogistic(String fulfillmentOrderId, String fulfillmentOrderPackageId) {
        HttpEntity<String> entity = new HttpEntity<>(savedHeaders);
        HttpEntity<String> response = restTemplate.exchange(
                urls.logisticViewPage + "?fulfillmentOrderId=" + fulfillmentOrderId + "&fulfillmentOrderPackageId=" + fulfillmentOrderPackageId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                });
        String html = response.getBody();
        // todo
        return html;
    }


    private HttpHeaders getCainiaoGlobalHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("cache-control", "no-cache");
        headers.add("pragma", "no-cache");
        headers.add("sec-fetch-user", "?1");
        headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
        headers.add("Sec-Fetch-Site", "none");
        headers.add("Sec-Fetch-Mode", "navigate");
        headers.add("Sec-Fetch-Dest", "document");
        headers.add("sec-ch-ua-platform", "\"macOS\"");
        headers.add("sec-ch-ua-mobile", "?0");
        headers.add("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"");
        headers.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.add("Accept-Encoding", "gzip, deflate, br");
        headers.add("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,und;q=0.7,ru;q=0.6");
        return headers;
    }
}
