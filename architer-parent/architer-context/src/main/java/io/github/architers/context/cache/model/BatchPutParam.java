package io.github.architers.context.cache.model;


import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.annotation.CachePut;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * @see CachePut
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BatchPutParam extends CacheChangeParam {

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
