package com.lz.thread.configuration;

import com.lz.thread.CommonTaskExecutor;
import com.lz.thread.properties.TaskExecutorProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 线程池配置类
 *
 * @author luyi
 */
@Configuration
@EnableConfigurationProperties(TaskExecutorProperties.class)
public class ThreadConfiguration {

    @Bean
    @ConditionalOnClass
    public CommonTaskExecutor commonTaskExecutor() {
        return new CommonTaskExecutor();
    }
}
