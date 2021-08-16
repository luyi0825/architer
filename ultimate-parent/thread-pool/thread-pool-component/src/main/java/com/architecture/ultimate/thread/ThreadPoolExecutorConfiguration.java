package com.architecture.ultimate.thread;

import com.architecture.ultimate.thread.properties.TaskExecutorProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
public class ThreadPoolExecutorConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "customize.thread-pool", name = "userCommon", havingValue = "true")
    public CommonTaskExecutor commonTaskExecutor() {
        return new CommonTaskExecutor();
    }
}
