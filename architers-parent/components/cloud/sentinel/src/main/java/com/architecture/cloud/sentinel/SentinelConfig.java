package io.github.architers.cloud.sentinel;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * Sentinel 配置类
 */
@Configuration
public class SentinelConfig {


    @Bean
    public UriBlockExceptionHandler customizeBlockExceptionHandler() {
        return new UriBlockExceptionHandler();
    }
}
