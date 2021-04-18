package com.lz.core.cache.common.operation;

import com.lz.core.cache.common.annotation.Cacheable;
import com.lz.core.cache.common.operation.CacheOperation;
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

}
