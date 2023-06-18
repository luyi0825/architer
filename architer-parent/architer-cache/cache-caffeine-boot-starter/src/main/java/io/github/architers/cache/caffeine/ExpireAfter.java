package io.github.architers.cache.caffeine;

import com.github.benmanes.caffeine.cache.Expiry;
import io.github.architers.context.cache.CacheConstants;
import org.checkerframework.checker.index.qual.NonNegative;


import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 */
public class ExpireAfter implements Expiry<String, Object> {

    static final long MAXIMUM_EXPIRY = (Long.MAX_VALUE >> 1); // 150 years


    public ExpireAfter() {

    }

    @Override
    public long expireAfterCreate(String key, Object value, long currentTime) {
        //有就用指定的时间
        Long expireTime = ExpireTimeLocal.get();
        if (expireTime != null && expireTime > 0) {
            return TimeUnit.NANOSECONDS.convert(expireTime, TimeUnit.MILLISECONDS);
        }

        //没有就永不过期
        return MAXIMUM_EXPIRY;
    }

    @Override
    public long expireAfterUpdate(String key, Object value, long currentTime, @NonNegative long currentDuration) {
        Long expireTime = ExpireTimeLocal.get();
        //有就用指定的时间
        if (expireTime != null){
            if(expireTime>0){
                return TimeUnit.MILLISECONDS.toNanos(expireTime);
            }
            if(CacheConstants.NEVER_EXPIRE==expireTime){
                return MAXIMUM_EXPIRY;
            }
        }
        //没有就用原来的时间
        return currentDuration;
    }

    @Override
    public long expireAfterRead(String key, Object value, long currentTime, @NonNegative long currentDuration) {
        Long expireTime = ExpireTimeLocal.get();
        //有就用指定的时间
        if (expireTime != null && expireTime > 0) {
            return TimeUnit.MILLISECONDS.toNanos(expireTime);
        }
        return currentDuration;

    }

}
