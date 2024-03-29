package com.yy.stock.bot.aliexpressbot.engine.rester;

import com.yy.stock.bot.engine.rester.ResterEngine;
import com.yy.stock.bot.helper.RestTemplateHelper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;

public class AliExpressResterEngine extends ResterEngine {
    @Override
    protected void initBotHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "text/html;charset=UTF-8");
        headers.add("Host", "https://www.aliexpress.com/");
        headers.add("cache-control", "no-cache");
        headers.add("pragma", "no-cache");
        headers.add("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"");
        headers.add("sec-ch-ua-mobile", "?0");
        headers.add("sec-ch-ua-platform", "\"macOS\"");
        headers.add("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");
        headers.add("sec-fetch-user", "?1");
        headers.add("upgrade-insecure-requests", "1");
        headers.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.add("Origin", "hhttps://www.aliexpress.com");
        headers.add("Sec-Fetch-Site", "none");
        headers.add("Sec-Fetch-Mode", "navigate");
        headers.add("Sec-Fetch-Dest", "document");
        headers.add("Referer", "https://www.aliexpress.com");
        headers.add("Accept-Encoding", "gzip, deflate, br");
        headers.add("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,und;q=0.7,ru;q=0.6");
        savedHeaders = headers;
    }

    @Override
    public String getStringResponse(String url) {
//        HttpEntity entity = new HttpEntity<>(savedHeaders);
//        HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
//        });
//        return response.getBody();

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<>(getBotHeaders());
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
    public String getGzipStringResponse(String url) {
        return null;
    }
}
