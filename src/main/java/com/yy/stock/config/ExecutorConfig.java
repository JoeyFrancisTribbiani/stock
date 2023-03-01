package com.yy.stock.config;

import com.yy.stock.common.util.VisibleThreadPoolTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig {

    @Value("${thread.pool.coreSize}")
    private int coreSize;

    @Value("${thread.pool.maxSize}")
    private int maxSize;

    @Value("${thread.pool.queueSize}")
    private int queueSize;

    @Value("${thread.pool.threadNamePrefix}")
    private String threadNamePrefix;

    /**
     * 线程池配置bean
     *
     * @return Executor
     */
    @Bean(name = "asyncServiceExecutor")
    public VisibleThreadPoolTaskExecutor asyncServiceExecutor() {
        log.info("start asyncServiceExecutor");
        // 使用 自定义 CustomThreadPoolTaskExecutor
        VisibleThreadPoolTaskExecutor executor = new VisibleThreadPoolTaskExecutor();
        // 配置核心线程数
        executor.setCorePoolSize(coreSize);
        // 配置最大线程数
        executor.setMaxPoolSize(maxSize);
        // 配置队列大小
        executor.setQueueCapacity(queueSize);
        // 配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(threadNamePrefix);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 执行初始化
        executor.initialize();
        return executor;
    }
}