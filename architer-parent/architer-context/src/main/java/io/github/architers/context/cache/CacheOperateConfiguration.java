package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.BatchPutOperationHandler;
import io.github.architers.context.cache.operate.CacheableOperationHandler;
import io.github.architers.context.cache.operate.DeleteCacheOperationHandler;
import io.github.architers.context.cache.operate.PutBaseCacheOperationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 */
@Configuration(proxyBeanMethods = false)
public class CacheOperateConfiguration {

    @Bean
    public PutBaseCacheOperationHandler publicPutCacheOperationHandler() {
        return new PutBaseCacheOperationHandler();
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
    public BatchPutOperationHandler batchPutOperationHandler() {
        return new BatchPutOperationHandler();
    }

}
