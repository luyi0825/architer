package com.lz.core.cache.redis;///**


import com.alibaba.fastjson.JSON;
import com.lz.core.cache.common.utils.CacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * <p>
 * 注意，过期的时间单位都是秒
 *
 * @author luyi
 * @date 2020-12-24
 */
@Component
public class RedisService {
    private static Logger logger = LoggerFactory.getLogger(RedisService.class);

    public RedisService() {
        logger.info("init RedisService");
    }


    @Autowired
    private StringRedisTemplate redisTemplate;

    //@Autowired(required = false)
    //private RedisTemplate<String, Object> redisTemplate;
    @Autowired(required = false)
    private ValueOperations<String, String> valueOperations;
    @Autowired(required = false)
    private HashOperations<String, String, Object> hashOperations;
    @Autowired(required = false)
    private ListOperations<String, Object> listOperations;
    @Autowired(required = false)
    private SetOperations<String, Object> setOperations;
    @Autowired(required = false)
    private ZSetOperations<String, Object> zSetOperations;


    /**
     * 描述:向redis中放入值:永不过期
     *
     * @param key   缓存的key
     * @param value 缓存的值：最终会以Json字符串的形式存贮
     * @author luyi
     * @date 2020/12/24 下午11:18
     */
    public boolean set(String key, Object value) {
        redisTemplate.opsForValue().set(key, CacheUtils.toJsonString(value));
        return true;
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
        redisTemplate.opsForValue().set(key,CacheUtils.toJsonString(value), expire, TimeUnit.SECONDS);
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
        return redisTemplate.opsForValue().setIfAbsent(key,CacheUtils.toJsonString(value));
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
        return redisTemplate.opsForValue().setIfAbsent(key,CacheUtils.toJsonString(value), expire, TimeUnit.SECONDS);
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
    public boolean setIfExist(String key, Object value, long expire) {
        return redisTemplate.opsForValue().setIfPresent(key,CacheUtils.toJsonString(value), expire, TimeUnit.SECONDS);

    }


    //*************************************get**************************************************/

    public String getAndSet(String key, Object value) {
        return this.redisTemplate.opsForValue().getAndSet(key,CacheUtils.toJsonString(value));
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
        return redisTemplate.opsForValue().get(key);
    }

    public String get(String key, long expire) {
        String value = redisTemplate.opsForValue().get(key);
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
        return value == null ? null : fromJson(value, clazz);
    }


    public long increment(String key, long value) {
        return redisTemplate.opsForValue().increment(key, value);
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


    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }


}
