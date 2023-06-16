package io.github.architers.context.cache.operate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存操作配置类
 *
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

}
