package com.lz.core.cache.common.operation;

import com.lz.core.cache.common.annotation.Cacheable;
import lombok.Data;

/**
 * @see com.lz.core.cache.common.annotation.PutCache 对应
 * @author luyi
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
}
