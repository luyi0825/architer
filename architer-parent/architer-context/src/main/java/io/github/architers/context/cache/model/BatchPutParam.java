package io.github.architers.context.cache.model;


import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.annotation.PutCache;
import lombok.Data;

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
    private Object batchCacheValue;

    /**
     * @see Cacheable#expireTime()
     */
    private long expireTime;

    /**
     * @see Cacheable#randomTime()
     */
    private long randomTime;

    /**
     * @see Cacheable#timeUnit()
     */
    private TimeUnit timeUnit;

}
