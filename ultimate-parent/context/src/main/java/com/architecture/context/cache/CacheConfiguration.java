package com.architecture.context.cache;


import com.architecture.context.cache.key.DefaultKeyGenerator;
import com.architecture.context.cache.key.KeyGenerator;
import com.architecture.context.cache.operation.CacheableOperationHandler;
import com.architecture.context.cache.operation.DeleteCacheOperationHandler;
import com.architecture.context.cache.operation.PutCacheOperationHandler;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 * 缓存配置，存放各种缓存的公共配置
 */
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfiguration {

    @Bean
    public CacheExpressionParser cacheExpressionParser() {
        return new CacheExpressionParser();
    }

    @Bean
    @ConditionalOnMissingBean
    public KeyGenerator keyGenerator(CacheExpressionParser cacheExpressionParser) {
        return new DefaultKeyGenerator(cacheExpressionParser);
    }

    @Bean
    @ConditionalOnMissingBean
    public CacheProcess cacheProcess() {
        return new DefaultCacheProcess();
    }

    @Bean
    public CacheableOperationHandler cacheableOperationHandler() {
        return new CacheableOperationHandler();
    }

    @Bean
    public DeleteCacheOperationHandler deleteCacheOperationHandler() {
        return new DeleteCacheOperationHandler();
    }

    @Bean
    public PutCacheOperationHandler putCacheOperationHandler() {
        return new PutCacheOperationHandler();
    }


}
