package io.github.architers.context.cache.operation;


import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.annotation.PutCache;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * @see PutCache
 */
@Data
public class PutCacheParam extends BaseCacheOperationParam {

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
    /**
     * 缓存值
     */
    private Object cacheValue;


    public long getRandomTime() {
        return randomTime;
    }

    public void setRandomTime(long randomTime) {
        this.randomTime = randomTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }



}
