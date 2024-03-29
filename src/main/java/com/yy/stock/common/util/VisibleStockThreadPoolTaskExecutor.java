package com.yy.stock.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: 腾腾
 * @Date: 2022/7/16/0016 22:19
 */
@Slf4j
public class VisibleStockThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

//    private static final Logger logger = LoggerFactory.getLogger(VisiableThreadPoolTaskExecutor.class);

    private void showThreadPoolInfo(String prefix) {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();

        log.info("{}, {},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}], isFull [{}]",
                this.getThreadNamePrefix(),
                prefix,
                threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(),
                threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size(),
                isFull());
    }

    public boolean isFull() {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
        return threadPoolExecutor.getActiveCount() == threadPoolExecutor.getCorePoolSize();
    }

    public int getIdleCount() {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
        return threadPoolExecutor.getCorePoolSize() - threadPoolExecutor.getActiveCount();
    }

    public boolean isEmpty() {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
        return threadPoolExecutor.getActiveCount() == 0;
    }

    @Override
    public void execute(Runnable task) {
        showThreadPoolInfo("1. do execute");
        super.execute(task);
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        showThreadPoolInfo("2. do execute");
        super.execute(task, startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        showThreadPoolInfo("1. do submit");
        return super.submit(task);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        showThreadPoolInfo("2. do submit");
        return super.submit(task);
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        showThreadPoolInfo("1. do submitListenable");
        return super.submitListenable(task);
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        showThreadPoolInfo("2. do submitListenable");
        return super.submitListenable(task);
    }
}