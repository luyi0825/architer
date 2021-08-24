package com.architecture.context.cache.operation;


import com.architecture.context.cache.CacheMode;
import com.architecture.context.cache.annotation.Cacheable;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * @see com.architecture.context.cache.annotation.PutCache
 */
@Data
public class PutCacheOperation extends BaseCacheOperation {

    /**
     * @see Cacheable#randomTime()
     */
    private long randomTime;
    /**
     * @see Cacheable#expireTime()
     */
    private long expireTime;

    /**
     * @see Cacheable#expireTimeUnit()
     */
    private TimeUnit expireTimeUnit;
    /**
     * 缓存值
     */
    private String cacheValue;

    /**
     * 缓存模式
     */
    private CacheMode cacheMode;
}
