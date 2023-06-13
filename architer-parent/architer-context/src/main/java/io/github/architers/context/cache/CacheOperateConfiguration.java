package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 */
@Configuration(proxyBeanMethods = false)
public class CacheOperateConfiguration {

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
    public BatchPutOperationHandler batchPutOperationHandler() {
        return new BatchPutOperationHandler();
    }

    @Bean
    public BatchEvictOperationHandler batchEvictOperationHandler() {
        return new BatchEvictOperationHandler();
    }

}
