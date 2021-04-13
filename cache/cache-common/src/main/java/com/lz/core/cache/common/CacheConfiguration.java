package com.lz.core.cache.common;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * 缓存配置，存放各种缓存的公共配置
 */
@Configuration
public class CacheConfiguration {

    @Bean
    @ConditionalOnBean
    public CacheProcess cacheProcess() {
        return new DefaultCacheProcess();
    }
}
