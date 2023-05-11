package com.yy.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
@EnableRetry
public class StockApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
        System.out.println("欢迎来到 YY Stock 系统！");
    }

}
