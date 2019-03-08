package com.en.adback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadExecutorConfig {

    @Bean("ThreadExecutor")
    public ExecutorService taskExecutor() {
        ExecutorService executorService = Executors.newWorkStealingPool();
        return executorService;
    }
}
