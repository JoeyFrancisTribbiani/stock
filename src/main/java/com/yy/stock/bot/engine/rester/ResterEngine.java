package com.yy.stock.bot.engine.rester;

import com.yy.stock.bot.engine.driver.MyCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public abstract class ResterEngine {
    @Autowired
    protected RestTemplate restTemplate;
    protected HttpHeaders savedHeaders;

    public ResterEngine() {
        initBotHeaders();
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public HttpHeaders getBotHeaders() {
        return savedHeaders;
    }

    protected abstract void initBotHeaders();

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

    public String getStringResponse(String url) {
        HttpEntity entity = new HttpEntity<>(savedHeaders);
        HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });
        return response.getBody();
    }

    //q:将下面的方法改为返回泛型方法
    public <T> T httpGetWithBotHeaders(String url, Class<T> clazz) {
        HttpEntity entity = new HttpEntity<>(savedHeaders);
        HttpEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, clazz);
        return response.getBody();
    }
}
