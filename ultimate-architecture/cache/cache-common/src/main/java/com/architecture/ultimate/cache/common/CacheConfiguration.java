package com.architecture.ultimate.cache.common;


import com.architecture.ultimate.cache.common.key.DefaultKeyGenerator;
import com.architecture.ultimate.cache.common.key.KeyGenerator;
import com.architecture.ultimate.cache.common.operation.CacheableOperationHandler;
import com.architecture.ultimate.cache.common.operation.DeleteCacheOperationHandler;
import com.architecture.ultimate.cache.common.operation.PutCacheOperationHandler;
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
