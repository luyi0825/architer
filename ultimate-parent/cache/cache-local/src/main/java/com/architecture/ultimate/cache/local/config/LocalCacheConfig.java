package com.architecture.ultimate.cache.local.config;


import com.architecture.ultimate.cache.local.CleanCacheExecutorService;
import com.architecture.ultimate.cache.local.properties.LocalCacheProperties;
import com.architecture.ultimate.cache.local.DefaultCleanCacheExecutorServiceImpl;
import com.architecture.ultimate.cache.local.LocalCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * 本地缓存配置类
 */
@Configuration
@EnableConfigurationProperties(LocalCacheProperties.class)
public class LocalCacheConfig {

    public LocalCacheConfig() {
        System.out.println("init LocalCacheConfig..");
    }

    @Autowired
    private LocalCacheProperties localCacheProperties;


    @Bean
    @ConditionalOnMissingBean
    public LocalCacheService localCache() {
        return LocalCacheService.build(localCacheProperties);
    }


    @ConditionalOnMissingBean
    @Bean
    public CleanCacheExecutorService cleanCacheExecutorService() {
        return new DefaultCleanCacheExecutorServiceImpl();
    }

}
