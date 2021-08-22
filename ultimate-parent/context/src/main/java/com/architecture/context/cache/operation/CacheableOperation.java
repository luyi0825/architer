package com.architecture.context.cache.operation;

import com.architecture.context.cache.annotation.Cacheable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * 对应Cacheable
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CacheableOperation extends CacheOperation {
    /**
     * @see Cacheable#randomTime()
     */
    private long randomTime;

    /**
     * @see Cacheable#randomTimeUnit()
     */
    private TimeUnit randomTimeUnit;

    /**
     * @see Cacheable#expireTime()
     */
    private long expireTime;

    /**
     * @see Cacheable#expireTimeUnit()
     */
    private TimeUnit expireTimeUnit;


}
