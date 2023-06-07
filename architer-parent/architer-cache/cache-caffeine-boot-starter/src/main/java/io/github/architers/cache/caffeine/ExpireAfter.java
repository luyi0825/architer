package io.github.architers.cache.caffeine;

import com.github.benmanes.caffeine.cache.Expiry;
import org.checkerframework.checker.index.qual.NonNegative;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 */
public class ExpireAfter implements Expiry<String, Object> {

    public CaffeineProperties caffeineProperties;

    public ExpireAfter(CaffeineProperties caffeineProperties) {
        this.caffeineProperties = caffeineProperties;
    }

    @Override
    public long expireAfterCreate(String key, Object value, long currentTime) {
        //有就用指定的时间
        Long expireTime = ExpireTimeLocal.get();
        if (expireTime != null && expireTime > 0) {
            return TimeUnit.NANOSECONDS.convert(expireTime, TimeUnit.SECONDS);
        }
        //没有就永不过期
        return caffeineProperties.getExpireNanosWhenNoSet();
    }

    @Override
    public long expireAfterUpdate(String key, Object value, long currentTime, @NonNegative long currentDuration) {
        Long expireTime = ExpireTimeLocal.get();
        //有就用指定的时间
        if (expireTime != null && expireTime > 0) {
            return TimeUnit.NANOSECONDS.convert(expireTime, TimeUnit.SECONDS);
        }
        //没有就用原来的时间
        return currentDuration;
    }

    @Override
    public long expireAfterRead(String key, Object value, long currentTime, @NonNegative long currentDuration) {
        Long expireTime = ExpireTimeLocal.get();
        //有就用指定的时间
        if (expireTime != null && expireTime > 0) {
            return TimeUnit.NANOSECONDS.convert(expireTime, TimeUnit.SECONDS);
        }
        //没有就用原来的时间
        return currentDuration;
    }

    public CaffeineProperties getCaffeineProperties() {
        return caffeineProperties;
    }
}
