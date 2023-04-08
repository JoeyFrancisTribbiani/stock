package com.yy.stock.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
@Slf4j
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());
//        factory.setConnectTimeout(5000);
        //q:解释一下这个factory的作用
        //a:factory是一个工厂，用来创建httpclient，这个httpclient是用来发送http请求的
        //q:为什么这个工厂名字要叫RequestFactory，听起来像是用来创建HttpRequest的
        //a:因为这个工厂是用来创建httpclient的，而httpclient是用来发送http请求的，所以这个工厂名字叫RequestFactory
        //q:为什么要用这个工厂来创建httpclient
        //a:因为这个工厂可以设置一些参数，比如超时时间，代理等等
        //q:为什么要设置超时时间
        //a:因为如果不设置超时时间，那么如果请求的网站不响应，那么这个请求就会一直等待下去，直到超时
        //q:为什么要设置代理
        //a:因为如果不设置代理，那么这个请求就会直接发送到目标网站，如果目标网站被墙了，那么就会请求失败
        //q:这个工厂是怎么运行的
        //a:当我们调用restTemplate.getForObject(url, String.class)的时候，这个工厂就会创建一个httpclient，然后用这个httpclient来发送请求
        //q:怎么得到工厂
        //a:通过@Bean注解，这个注解会告诉spring，这个方法会返回一个对象，然后spring会把这个对象放到容器里面，然后我们就可以通过@Autowired注解来获取这个对象
        //q:这个工厂内部使用了什么模式
        //a:工厂模式
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
                log.info("some error!");
            }
        });
        return restTemplate;
    }
}
