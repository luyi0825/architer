package com.lz.core.cache.redis;

import com.lz.core.cache.common.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 * redis注解缓存操作
 *
 * @author luyi
 */
public class CustomRedisCacheManager implements CacheManager {

    private StringRedisService stringRedisService;



    @Override
    public Object getCache(String key) {
        return stringRedisService.get(key);
    }

    @Override
    public Object putCache(String key, Object value, long expireTime) {
        stringRedisService.set(key, value, expireTime);
        return value;
    }

    @Override
    public void deleteCache(String key) {
        stringRedisService.delete(key);
    }

    @Autowired
    public void setStringRedisService(StringRedisService stringRedisService) {
        this.stringRedisService = stringRedisService;
    }

}
