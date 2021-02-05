package com.zht.emqdemo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @describe 线程池配置
 * @author zht
 * @date 2021/1/18
 */
@Configuration
public class ExecutorConfig {

    /**
     * 核心线程池大小
     */
    private final int corePoolSize = 10;

    /**
     * 最大可创建的线程数
     */
    private final int maxPoolSize = 20;

    /**
     * 队列最大长度
     */
    private final int queueCapacity = 10000;

    /**
     * 线程池维护线程所允许的空闲时间
     */
    private final int keepAliveSeconds = 60;

    /**
     * 异步线程池
     *
     * @return Executor
     */
    @Bean
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("async-");
        // 线程池对拒绝任务(无线程可用)的处理策略，不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 默认线程池
     *
     * @return Executor
     */
    @Bean
    public ThreadPoolTaskExecutor mqttThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("mqtt_");
        executor.initialize();
        return executor;
    }
}
