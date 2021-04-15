package com.lz.core.cache.common;


import com.lz.core.cache.common.key.DefaultKeyGenerator;
import com.lz.core.cache.common.key.KeyGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * 缓存配置，存放各种缓存的公共配置
 */
@Configuration
public class CacheConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public KeyGenerator keyGenerator() {
        return new DefaultKeyGenerator();
    }

}
