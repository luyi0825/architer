package com.lz.core.cache.redis;


import com.lz.core.cache.common.CacheConstants;
import com.lz.core.cache.common.utils.JsonUtils;
import lombok.NonNull;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类--处理字符串类型
 * <p>
 * 注意，过期的时间单位都是秒
 *
 * @author luyi
 * @date 2020-12-24
 */
@Component
public class StringRedisService {

    private final StringRedisTemplate redisTemplate;
    private final ValueOperations<String, String> valueOperations;

    @NonNull
    public StringRedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        valueOperations = redisTemplate.opsForValue();
    }

    /**
     * 描述:向redis中放入值:永不过期
     *
     * @param key   缓存的key
     * @param value 缓存的值：最终会以Json字符串的形式存贮
     * @author luyi
     * @date 2020/12/24 下午11:18
     */
    public void set(String key, Object value) {
        valueOperations.set(key, JsonUtils.toJsonString(value));
    }

    /**
     * 描述:向redis中放入值
     * <P>设置缓存不过期，看set(String key, Object value)方法</P>
     *
     * @param expire 过期时间 单位秒
     * @param key    缓存的key
     * @param value  缓存的值：最终会以Json字符串的形式存贮
     * @author luyi
     * @date 2020/12/24 下午11:18
     */
    public void set(String key, Object value, long expire) {
        if (expire <= 0) {
            throw new IllegalArgumentException("expire必须大于0");
        }
        valueOperations.set(key, JsonUtils.toJsonString(value), expire, TimeUnit.SECONDS);
    }

    /**
     * 描述:如果不存在，就向缓存中设置值(设置的值不过期)
     *
     * @param key   缓存的key
     * @param value 缓存的值：最终会以Json字符串的形式存贮
     * @author luyi
     * @date 2020/12/24 下午11:34
     */
    public boolean setIfNotExist(String key, Object value) {
        return valueOperations.setIfAbsent(key, JsonUtils.toJsonString(value));
    }

    /**
     * 描述:如果不存在，就向缓存中设置值
     *
     * @param expire 过期的时间，单位秒
     * @param key    缓存的key
     * @param value  缓存的值：最终会以Json字符串的形式存贮
     * @author luyi
     * @date 2020/12/24 下午11:34
     */
    public boolean setIfNotExist(String key, Object value, long expire) {
        if (expire < 0) {
            throw new IllegalArgumentException("expire必须大于0");
        }
        return valueOperations.setIfAbsent(key, JsonUtils.toJsonString(value), expire, TimeUnit.SECONDS);
    }

    /**
     * 描述:如果存在，就向缓存中设置值
     *
     * @param expire 过期的时间，单位秒
     * @param key    缓存的key
     * @param value  缓存的值：最终会以Json字符串的形式存贮
     * @return 是否成功
     * @author luyi
     * @date 2020/12/24 下午11:34
     */
    @NonNull
    public boolean setIfExist(String key, Object value, long expire) {
        return valueOperations.setIfPresent(key, JsonUtils.toJsonString(value), expire, TimeUnit.SECONDS);

    }


    //*************************************get**************************************************/

    public String getAndSet(String key, Object value) {
        return valueOperations.getAndSet(key, JsonUtils.toJsonString(value));
    }

    /**
     * 描述:得到缓存值，如果之前存的是对象，返回的就是json字符串
     *
     * @param key 缓存的key
     * @author luyi
     * @date 2020/12/24 下午11:47
     * @TODO 没有值的时候，验证下是返回null还是-2
     */
    public String get(String key) {
        return valueOperations.get(key);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        //缓存中有值的时候才设置过期的时间
        if (value != null) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    /**
     * 描述:得到值，并设置过期时间
     *
     * @author luyi
     * @date 2020/12/24 下午11:52
     */
    public <T> T getAndSetExpire(String key, Class<T> clazz, long expire) {
        if (expire != CacheConstants.NEVER_EXPIRE && expire <= 0) {
            throw new IllegalArgumentException("expire的值不合法");
        }
        T value = get(key, clazz);

        if (value == null) {
            return null;
        }
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        return value;
    }


    public <T> T get(String key, Class<T> clazz) {
        String value = valueOperations.get(key);
        return value == null ? null : JsonUtils.readValue(value, clazz);
    }

    /**
     * 上升
     * @param key 缓存的key
     * @param value 上升的值
     * @return 上升后的值
     */
    public long increment(String key, long value) {
        return valueOperations.increment(key, value);
    }

    /**
     * 描述:删除缓存
     *
     * @author luyi
     * @date 2020/12/25 上午12:03
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
