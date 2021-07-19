package com.architecture.ultimate.log.executor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 */
@Configuration
@EnableConfigurationProperties(LogProperties.class)
public class LogExecutorConfig {
//
//    @Bean
//    @ConditionalOnProperty(prefix = "customize.log",name="async",havingValue = "true")
//    public LogExecutor logExecutor(LogProperties logProperties) {
//        return new LogExecutor(logProperties);
//    }
}
