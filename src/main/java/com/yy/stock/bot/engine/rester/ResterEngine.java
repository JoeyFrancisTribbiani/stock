package com.yy.stock.bot.engine.rester;

import cn.hutool.extra.spring.SpringUtil;
import com.yy.stock.bot.engine.driver.MyCookie;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public abstract class ResterEngine  {
    protected RestTemplate restTemplate;
    protected HttpHeaders savedHeaders;

    public ResterEngine() {
        restTemplate = SpringUtil.getBean(RestTemplate.class);
        initBotHeaders();
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public HttpHeaders getBotHeaders() {
        return savedHeaders;
    }

    protected abstract void initBotHeaders();

    public abstract String getStringResponse(String url);
    public abstract String getGzipStringResponse(String url) ;

    public void updateCookie(MyCookie[] cookies) {
        StringBuilder builder = new StringBuilder();
        for (var cookie : cookies) {
            builder.append(cookie.getName())
                    .append("=")
                    .append(cookie.getValue())
                    .append(";");
        }
        if (builder.isEmpty()) {
            return;
        }
        savedHeaders.set("Cookie", builder.toString());
    }

//    public void updateHeaders(HttpHeaders headers) {
//        savedHeaders = headers;
//    }


    //q:将下面的方法改为返回泛型方法
    public <T> T httpGetWithBotHeaders(String url, Class<T> clazz) {
        HttpEntity entity = new HttpEntity<>(savedHeaders);
        HttpEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, clazz);
        return response.getBody();
    }


}