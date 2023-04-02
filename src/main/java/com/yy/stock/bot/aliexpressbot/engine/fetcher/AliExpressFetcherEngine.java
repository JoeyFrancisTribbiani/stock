package com.yy.stock.bot.aliexpressbot.engine.fetcher;

import com.yy.stock.bot.helper.RestTemplateHelper;
import com.yy.stock.config.GlobalVariables;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class AliExpressFetcherEngine {


    private String reqeustHtml(String url) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(savedHeaders);
        HttpEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                byte[].class
        );
        byte[] data = RestTemplateHelper.unGZip(new ByteArrayInputStream(response.getBody()));

        var result = new String(data, "UTF-8");

        return result;
    }

    public String getProductHtmlSource(String url) throws IOException, InterruptedException {
        var cookieSet = getBuyerAccountCookie();
        updateCookiesInHeaders(cookieSet);
        var html = reqeustHtml(url);
        if (html.contains("imageModule")) {
            updateLoginTime();
            return html;
        }
        if (html.contains("Page Not Found")) {
            return GlobalVariables.PRODUCT_PAGE_NOT_FOUND;
        }

        login();
        getDriver().get(url);
        return getDriver().getPageSource();
//        return reqeustHtml(url);
    }
}
