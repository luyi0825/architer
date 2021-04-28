package com.lz.core.cache.common;


import com.lz.core.cache.common.key.DefaultKeyGenerator;
import com.lz.core.cache.common.key.KeyGenerator;
import com.lz.core.cache.common.operation.CacheableOperationHandler;
import com.lz.core.cache.common.operation.DeleteCacheOperationHandler;
import com.lz.core.cache.common.operation.PutCacheOperationHandler;
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
