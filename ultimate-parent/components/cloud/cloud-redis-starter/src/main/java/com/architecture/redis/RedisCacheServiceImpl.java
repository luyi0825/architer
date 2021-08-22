package com.architecture.redis;


import com.architecture.context.cache.CacheService;
import com.architecture.utils.JsonUtils;
import org.springframework.data.redis.core.*;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类--处理字符串类型
 * <p>
 * 注意，过期的时间单位都是秒
 *
 * @author luyi
 * @date 2020-12-24
 */
public class RedisCacheServiceImpl implements CacheService {
    private static final String REDIS_SPLIT = "::";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> valueOperations;

    public RedisCacheServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public String getSplit() {
        return REDIS_SPLIT;
    }

    @Override
    public void set(String key, Object value) {
        valueOperations.set(key, value);
    }

    @Override
    public void set(Map<String, Object> map) {
        valueOperations.multiSet(map);
    }


    @Override
    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        valueOperations.set(key, value, expire, timeUnit);
    }

    @Override
    public boolean setIfAbsent(String key, Object value) {
        Boolean bool = valueOperations.setIfAbsent(key, value);
        if (bool == null) {
            return false;
        }
        return bool;
    }

    @Override
    public boolean setIfAbsent(String key, Object value, long expire, TimeUnit timeUnit) {
        Boolean bool = valueOperations.setIfAbsent(key, value, expire, timeUnit);
        if (bool == null) {
            return false;
        }
        return bool;
    }

    @Override
    public boolean setIfPresent(String key, Object value) {
        Boolean bool = valueOperations.setIfPresent(key, value);
        if (bool == null) {
            return false;
        }
        return bool;
    }

    @Override
    public boolean setIfPresent(String key, Object value, long expire, TimeUnit timeUnit) {
        Boolean bool = valueOperations.setIfPresent(key, value, expire, timeUnit);
        if (bool == null) {
            return false;
        }
        return bool;
    }


    //*************************************get**************************************************/

    @Override
    public Object getAndSet(String key, Object value) {
        return valueOperations.getAndSet(key, value);
    }

    @Override
    public Object get(String key) {
        return valueOperations.get(key);
    }

    @Override
    public List<Object> multiGet(Collection<String> keys) {
        return valueOperations.multiGet(keys);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        Object value = valueOperations.get(key);
        if (value instanceof String) {
            return JsonUtils.readValue((String) value, clazz);
        }
        return (T) value;
    }


    @Override
    public boolean delete(String key) {
        if (key == null) {
            return false;
        }
        Boolean bool = redisTemplate.delete(key);
        if (bool == null) {
            return false;
        }
        return bool;
    }

    @Override
    public long multiDelete(Collection<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return 0L;
        }
        Long count = redisTemplate.delete(keys);
        if (count == null) {
            return 0;
        }
        return count;
    }


}
