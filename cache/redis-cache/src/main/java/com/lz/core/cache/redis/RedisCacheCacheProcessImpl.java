package com.lz.core.cache.redis;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lz.core.cache.common.CacheConstants;
import com.lz.core.cache.common.CacheProcess;
import com.lz.core.cache.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

import java.util.concurrent.locks.Lock;

/**
 * redis缓存处理器
 *
 * @author luyi
 * @date 2020/12/17
 */
@Component
@Slf4j
public class RedisCacheCacheProcessImpl implements Ordered, CacheProcess {


    @Autowired
    private StringRedisService stringRedisService;
    @Autowired
    private RedissonClient redissonClient;


    @Override
    public String getCacheKey(Method method, Object... args) {
        return null;
    }

    @Override
    public Object process(Object target, Method method, Object[] args) {
        String cacheKey = getCacheKey(method, args);
        Object cacheValue = stringRedisService.get(cacheKey);
        if (cacheValue == null) {
            cacheValue = this.getData(target, method, args, cacheKey);
        }
        return jsonValueConvert(cacheValue);
    }

    /**
     * 描述:转换缓存值
     *
     * @author luyi
     * @date 2020/12/26 上午1:11
     */
    private Object jsonValueConvert(Object value) {
        //放入的假的缓存值，直接返回null
        if (CacheConstants.CACHE_NOT_EXIST.equals(value)) {
            return null;
        }
        //从数据库中获取的值
        if (!(value instanceof String)) {
            return value;
        }
        Method method = null;
        Class<?> returnType = method.getReturnType();

        if (returnType instanceof Object) {

        }
        ObjectMapper objectMapper = new ObjectMapper();
        // JavaType listType = objectMapper.getTypeFactory().constructParametricType(returnType, clazz);

        //需要将jsonString 反序列化
        //  return JsonUtils.readValue(value, returnType.getClass());
        return null;
    }

    public Object getData(Object target, Method method, Object[] args, String cacheKey) {
        Lock lock = redissonClient.getLock(cacheKey + ".lock");
        //加分布式锁
        lock.lock();
        try {
            //再次判断缓存中是否有值
            String cacheValue = stringRedisService.get(cacheKey);
            if (cacheValue == null) {
                //反射调用方法
                Object value = method.invoke(target, args);
                if (value == null) {
                    //缓存不存在，放0,防止缓存穿透
                    stringRedisService.set(cacheKey, CacheConstants.CACHE_NOT_EXIST, 30 * 60);
                } else {
                    stringRedisService.set(cacheKey, JsonUtils.toJsonString(value), 30 * 60);
                }
                return value;
            }
            return cacheValue;
        } catch (Exception e) {
            throw new RuntimeException("获取数据失败", e);
        } finally {
            //释放分布式锁
            lock.unlock();
        }
    }


    /**
     * 小的在前边
     */
    @Override
    public int getOrder() {
        return 10;
    }
}
