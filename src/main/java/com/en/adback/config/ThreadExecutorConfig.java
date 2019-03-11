package com.en.adback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author  YSH
 * @create 20190308
 * @desc  配置线程池(主要用于策略是否分发到设备,其他地方使用可以直接注入即可)
 */
@Configuration
public class ThreadExecutorConfig {

    @Bean("ThreadExecutor")
    public ExecutorService taskExecutor() {
        ExecutorService executorService = Executors.newWorkStealingPool();
        return executorService;
    }
}
