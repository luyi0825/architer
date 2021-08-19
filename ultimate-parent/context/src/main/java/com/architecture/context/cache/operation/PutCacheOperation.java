package com.architecture.context.cache.operation;


import lombok.Data;

/**
 * @author luyi
 * @see PutCache 对应
 */
@Data
public class PutCacheOperation extends CacheOperation {
    /**
     * @see Cacheable#randomExpireTime()
     */
    private long randomExpireTime;

    /**
     * 缓存失效时间
     *
     * @see Cacheable#expireTime()
     */
    private long expireTime;

    /**
     * 缓存值
     *
     * @see Cacheable#cacheValue()
     */
    private String cacheValue;
}
