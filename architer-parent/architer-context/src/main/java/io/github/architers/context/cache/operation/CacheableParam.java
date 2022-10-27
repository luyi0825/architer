package io.github.architers.context.cache.operation;

import io.github.architers.context.cache.annotation.Cacheable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * 对应Cacheable
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CacheableParam extends BaseCacheOperationParam {
    /**
     * @see Cacheable#randomTime()
     */
    private long randomTime;

    /**
     * @see Cacheable#expireTime()
     */
    private long expireTime;

    /**
     * @see Cacheable#timeUnit()
     */
    private TimeUnit timeUnit;

}
