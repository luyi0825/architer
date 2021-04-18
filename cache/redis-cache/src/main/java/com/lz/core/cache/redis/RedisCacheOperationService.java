package com.lz.core.cache.redis;

import com.lz.core.cache.common.CacheOperationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * redis注解缓存操作
 * @author luyi
 */
public class RedisCacheOperationService implements CacheOperationService {

    private StringRedisService stringRedisService;


    @Override
    public Object getCache(String key) {
        return stringRedisService.get(key);
    }

    @Override
    public Object putCache(String key, Object value) {
        stringRedisService.set(key, value);
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
