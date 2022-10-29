package io.github.architers.context.cache.operation;


import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.annotation.PutCache;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * @see PutCache
 */
@Data
public class BatchPutParam implements CacheOperationParam {


    /**
     * 缓存名称
     */
    private String cacheName;

    /**
     * 是否异步
     */
    private boolean async = false;

    /**
     * 缓存值
     */
    private Map<Object, Object> batchCacheValue;


}
