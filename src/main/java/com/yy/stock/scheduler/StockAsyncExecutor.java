package com.yy.stock.scheduler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class StockAsyncExecutor {
    @Async("asyncServiceExecutor")
    public void doStockAsync() {
        System.out.println("进入 asyncServiceExecutor");
    }
}
