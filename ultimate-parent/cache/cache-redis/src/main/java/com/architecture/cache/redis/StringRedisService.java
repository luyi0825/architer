package com.architecture.cache.redis;


import com.architecture.cache.Constants;
import com.architecture.utils.JsonUtils;
import lombok.NonNull;
import org.springframework.data.redis.core.*;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类--处理字符串类型
 * <p>
 * 注意，过期的时间单位都是秒
 *
 * @author luyi
 * @date 2020-12-24
 */
public class StringRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ValueOperations<String, Object> valueOperations;


    public StringRedisService(RedisTemplate<String, Object> redisTemplate) {
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
        valueOperations.set(key, value);
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
        if (expire == Constants.NEVER_EXPIRE) {
            this.set(key, value);
        }
        if (expire <= 0) {
            throw new IllegalArgumentException("expire必须大于0");
        }
        valueOperations.set(key, value, expire, TimeUnit.SECONDS);
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
        return valueOperations.setIfAbsent(key, value);
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
        return valueOperations.setIfAbsent(key, value, expire, TimeUnit.SECONDS);
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
        return valueOperations.setIfPresent(key, value, expire, TimeUnit.SECONDS);

    }


    //*************************************get**************************************************/

    public Object getAndSet(String key, Object value) {
        return valueOperations.getAndSet(key, value);
    }

    /**
     * 描述:得到缓存值，如果之前存的是对象，返回的就是json字符串
     *
     * @param key 缓存的key
     * @author luyi
     * @date 2020/12/24 下午11:47
     * @TODO 没有值的时候，验证下是返回null还是-2
     */
    public Object get(String key) {
        return valueOperations.get(key);
    }

    public Object get(String key, long expire) {
        Object value = valueOperations.get(key);
        //缓存中有值的时候才设置过期的时间
        if (value != null) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    /**
     * 描述:得到值，并设置过期时间
     * 只适合存储的普通字符串类型
     *
     * @author luyi
     * @date 2020/12/24 下午11:52
     */
    public <T> T getAndSetExpire(String key, Class<T> clazz, long expire) {
        if (expire != Constants.NEVER_EXPIRE && expire <= 0) {
            throw new IllegalArgumentException("expire的值不合法");
        }
        T value = get(key, clazz);
        if (value == null) {
            return null;
        }
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        return value;
    }


    /**
     * 只适合存储的普通字符串类型
     *
     * @author luyi
     * @date 2021/4/23
     */
    public <T> T get(String key, Class<T> clazz) {
        String value = (String) valueOperations.get(key);
        return JsonUtils.readValue(value, clazz);
    }

    /**
     * 上升
     *
     * @param key   缓存的key
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
