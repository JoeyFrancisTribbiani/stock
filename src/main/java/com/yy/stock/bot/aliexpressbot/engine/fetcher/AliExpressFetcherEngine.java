package com.yy.stock.bot.aliexpressbot.engine.fetcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yy.stock.bot.engine.fetcher.FetcherEngine;
import com.yy.stock.bot.helper.RestTemplateHelper;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class AliExpressFetcherEngine extends FetcherEngine {
    protected String fetchHtml(String url) throws IOException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<>(resterEngine.getBotHeaders());
            HttpEntity<byte[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    byte[].class
            );

            byte[] data = RestTemplateHelper.unGZip(new ByteArrayInputStream(response.getBody()));

            return new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String fetchHtmlByDriver(String url) throws IOException {
        return this.driverEngine.getHtml(url);
    }

    /**
     * @param html
     * @return
     */
    @Override
    protected boolean verifyHtml(String html) {
        return html.contains("imageModule");
    }

    /**
     * @param html
     * @return
     */
    @Override
    protected boolean verifyPageNotFound(String html) {
        return html.contains("Page Not Found");
    }

    /**
     * @param html
     * @return
     */
    @Override
    protected String fetchSkuProperties(String html) throws JsonProcessingException {
        var jsonStr = "";
        Document doc = Jsoup.parse(html);
        Elements tds = doc.getElementsByTag("script"); // 标识获取html中第一个<script>标签
        for (var element : tds) {
            var content = element.data();
            content = content.trim();
            if (content.startsWith("window.runParams")) {
                jsonStr = content.split("window.runParams = \\{")[1];
                jsonStr = jsonStr.trim();
                jsonStr = content.split("data: ")[1];
                jsonStr = jsonStr.split("csrfToken: ")[0];
                jsonStr = jsonStr.trim();
                jsonStr = StringUtils.removeEnd(jsonStr, ",");
                System.out.println("json:" + jsonStr);
            }
        }
        if (jsonStr.equals("")) {
            return html;
        }
        var platfromJsonStr = new ObjectMapper().writeValueAsString(coreEngine.getBuyerAccount().getPlatform());
        platfromJsonStr = "\"platform\":" + platfromJsonStr + ",";
        var sb = new StringBuilder(jsonStr);
        var i = jsonStr.indexOf("{");
        sb.insert(i + 1, platfromJsonStr);
        jsonStr = sb.toString();
        return jsonStr;
    }

}
