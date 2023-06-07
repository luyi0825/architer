package io.github.architers.cache.caffeine;

import com.github.benmanes.caffeine.cache.Expiry;
import org.checkerframework.checker.index.qual.NonNegative;

import java.io.Serializable;

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
        Long expireTime = ExpireTimeLocal.get();
        if (expireTime != null && expireTime > 0) {
            return expireTime * 1000L;
        }
        return caffeineProperties.getExpireNanosWhenNoSet();
    }

    @Override
    public long expireAfterUpdate(String key, Object value, long currentTime, @NonNegative long currentDuration) {
        Long expireTime = ExpireTimeLocal.get();
        if (expireTime != null && expireTime > 0) {
            return expireTime * 1000L;
        }
        return caffeineProperties.getExpireNanosWhenNoSet();
    }

    @Override
    public long expireAfterRead(String key, Object value, long currentTime, @NonNegative long currentDuration) {
        return currentDuration;
    }

    public CaffeineProperties getCaffeineProperties() {
        return caffeineProperties;
    }
}
