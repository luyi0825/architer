package com.architecture.ultimate.cache.common.operation;

import com.architecture.ultimate.cache.common.annotation.Cacheable;
import lombok.Data;

/**
 * @author luyi
 * 对应Cacheable
 */
@Data
public class CacheableOperation extends CacheOperation {
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
