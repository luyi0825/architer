package com.lz.core.cache.common;

import com.lz.core.cache.common.annotation.Cacheable;
import lombok.Data;

/**
 * @author luyi
 * 对应Cacheable
 */
@Data
public class CacheableCacheOperation extends CacheOperation {
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

}
