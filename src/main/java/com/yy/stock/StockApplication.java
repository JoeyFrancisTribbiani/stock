package com.yy.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
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
