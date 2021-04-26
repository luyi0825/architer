package com.lz.core.cache.common.operation;

import com.lz.core.cache.common.annotation.Cacheable;
import lombok.Data;

/**
 * @author luyi
 * @see com.lz.core.cache.common.annotation.PutCache 对应
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
