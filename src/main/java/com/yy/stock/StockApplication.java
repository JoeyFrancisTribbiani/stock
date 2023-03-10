package com.yy.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableRetry
public class StockApplication {
    public static void main(String[] args) {
        try {
            System.out.println("test");
            SpringApplication.run(StockApplication.class, args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
