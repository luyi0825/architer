package com.architecture.redis.support.cache;

import com.architecture.context.cache.CacheConstants;
import com.architecture.utils.JsonUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 */
public class RedisValueService {

    private RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> valueOperations;

    public RedisValueService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }


    public void set(String key, Object value) {
        valueOperations.set(key, value);
    }


    public void set(Map<String, Object> map) {
        valueOperations.multiSet(map);
    }


    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        if (expire == CacheConstants.NEVER_EXPIRE) {
            this.set(key, value);
        } else {
            valueOperations.set(key, value, expire, timeUnit);
        }
    }


    public boolean setIfAbsent(String key, Object value) {
        Boolean bool = valueOperations.setIfAbsent(key, value);
        if (bool == null) {
            return false;
        }
        return bool;
    }


    public boolean setIfAbsent(String key, Object value, long expire, TimeUnit timeUnit) {
        Boolean bool = valueOperations.setIfAbsent(key, value, expire, timeUnit);
        if (bool == null) {
            return false;
        }
        return bool;
    }


    public boolean setIfPresent(String key, Object value) {
        Boolean bool = valueOperations.setIfPresent(key, value);
        if (bool == null) {
            return false;
        }
        return bool;
    }

    public boolean setIfPresent(String key, Object value, long expire, TimeUnit timeUnit) {
        Boolean bool = valueOperations.setIfPresent(key, value, expire, timeUnit);
        if (bool == null) {
            return false;
        }
        return bool;
    }


    //*************************************get**************************************************/


    public Object getAndSet(String key, Object value) {
        return valueOperations.getAndSet(key, value);
    }


    public Object get(String key) {
        return valueOperations.get(key);
    }


    public List<Object> multiGet(Collection<String> keys) {
        return valueOperations.multiGet(keys);
    }


    public <T> T get(String key, Class<T> clazz) {
        Object value = valueOperations.get(key);
        if (value instanceof String) {
            return JsonUtils.readValue((String) value, clazz);
        }
        return (T) value;
    }

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
