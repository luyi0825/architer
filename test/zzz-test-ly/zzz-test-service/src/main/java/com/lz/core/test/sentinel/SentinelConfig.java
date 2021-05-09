package com.lz.core.test.sentinel;

import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.WebFluxCallbackManager;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * Sentinel 配置类
 */
@Configuration
public class SentinelConfig {
    @Bean
    public CustomeBlockExceptionHandler customeBlockExceptionHandler() {
        return new CustomeBlockExceptionHandler();
    }
}
