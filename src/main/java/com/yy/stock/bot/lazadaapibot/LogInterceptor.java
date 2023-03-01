package com.yy.stock.bot.lazadaapibot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Configuration
@Slf4j
public class LogInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        recordRequest(request,body);
        ClientHttpResponse httpResponse = execution.execute(request,body);
        recordResponse(httpResponse);
        return httpResponse;
    }
    private void recordRequest(HttpRequest httpRequest,byte[] body) {
        log.debug("请求地址：{},请求方法：{}", httpRequest.getURI(), httpRequest.getMethod());
        log.debug("请求header:{}", httpRequest.getHeaders());
        log.debug("请求body:{}", new String(body, StandardCharsets.UTF_8));
        log.debug("请求开始---------------------------------");
    }
    public void  recordResponse(ClientHttpResponse response) throws  IOException{

        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(response.getBody(),StandardCharsets.UTF_8));

        String line = bufferedReader.readLine();
        while (line!=null){
                inputStringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
        }
        log.debug("Status code  : [{}]", response.getStatusCode());
        log.debug("Status text  : [{}]", response.getStatusText());
        log.debug("Headers      : [{}]", response.getHeaders());
        log.debug("Response body: [{}]", inputStringBuilder);
    }
}
