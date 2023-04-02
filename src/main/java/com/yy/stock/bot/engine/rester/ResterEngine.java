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

    protected abstract void initBotHeaders();

    public void updateHeaders(HttpHeaders headers) {
        savedHeaders = headers;
    }

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

    private void updateHeaders(String key, String value) {
        savedHeaders.set(key, value);
    }

    public String getStringResponse(String url) {
        HttpEntity entity = new HttpEntity<>(savedHeaders);
        HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });
        return response.getBody();
    }
}
