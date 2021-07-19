package com.architecture.ultimate.cache.common.operation;

import com.architecture.ultimate.cache.common.annotation.Cacheable;
import com.architecture.ultimate.cache.common.annotation.PutCache;
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
