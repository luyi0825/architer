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
public class PutParam extends CacheChangeParam   {

    /**
     * 缓存的key
     */
    private String key;

    /**
     * 缓存的过期时间
     */
    private long expireTime;

    /**
     * @see Cacheable#timeUnit()
     */
    private TimeUnit timeUnit;
    /**
     * 缓存值
     */
    private Object cacheValue;


}
