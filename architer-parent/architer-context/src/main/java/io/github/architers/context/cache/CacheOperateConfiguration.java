package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.BatchPutOperationHandler;
import io.github.architers.context.cache.operate.CacheableOperationHandler;
import io.github.architers.context.cache.operate.DeleteCacheOperationHandler;
import io.github.architers.context.cache.operate.PutCacheOperationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luyi
 */
@Configuration(proxyBeanMethods = false)
public class CacheOperateConfiguration {

    @Bean
    public PutCacheOperationHandler publicPutCacheOperationHandler() {
        return new PutCacheOperationHandler();
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
