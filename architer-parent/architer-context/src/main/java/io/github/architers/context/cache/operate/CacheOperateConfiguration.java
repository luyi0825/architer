package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.operate.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 */
@Configuration(proxyBeanMethods = false)
public class CacheOperateConfiguration {

    @Bean
    public CacheOperateSupport cacheOperateFactory() {
        return new CacheOperateSupport();
    }

    @Bean
    public CachePutOperationHandler putCacheOperationHandler() {
        return new CachePutOperationHandler();
    }


    @Bean
    public CacheableOperationHandler cacheableOperationHandler() {
        return new CacheableOperationHandler();
    }

    @Bean
    public CacheEvictOperationHandler deleteCacheOperationHandler() {
        return new CacheEvictOperationHandler();
    }

    @Bean
    public CacheBatchPutOperationHandler batchPutOperationHandler() {
        return new CacheBatchPutOperationHandler();
    }

    @Bean
    public CacheBatchEvictOperationHandler batchEvictOperationHandler() {
        return new CacheBatchEvictOperationHandler();
    }


    @Bean
    public CacheEvictAllOperationHandler cacheEvictAllOperationHandler() {
        return new CacheEvictAllOperationHandler();
    }

    @Bean
    public CacheBatchEvictOperationHandler cacheBatchEvictOperationHandler() {
        return new CacheBatchEvictOperationHandler();
    }

    @Bean
    public CacheOperateManager cacheOperateManager() {
        return new CacheOperateManager();
    }
}
