package com.lz.core.cache.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lz.core.cache.process.CacheProcess;
import com.lz.core.cache.common.utils.CacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.locks.Lock;

/**
 * redis缓存处理器
 *
 * @author luyi
 * @date 2020/12/17
 */
@Component
@Slf4j
public class RedisCacheCacheProcessImpl extends CacheProcess implements Ordered {


    @Autowired
    private RedisService redisService;
    @Autowired
    private RedissonClient redissonClient;


    @Override
    public boolean supportBefore(Method method) {
        return true;
    }

    @Override
    public Object processCache(ProceedingJoinPoint jp) throws Throwable {
        String cacheKey = getCacheKey(jp);
        Object cacheValue = redisService.get(cacheKey);
        if (cacheValue == null) {
            cacheValue = this.getData(jp, cacheKey);
        }
        return convertJsonValue(jp, cacheValue);
    }

    /**
     * 描述:转换缓存值
     *
     * @author luyi
     * @date 2020/12/26 上午1:11
     */
    private Object convertJsonValue(ProceedingJoinPoint jp, Object value) throws IllegalAccessException, InstantiationException {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        //方法返回的参数类型
        Class<?> returnType = methodSignature.getMethod().getReturnType();
        //放入的假的缓存值，直接返回null
        if (CacheConstants.CACHE_NOT_EXIST.equals(value)) {
            return null;
        }
        //说明查询数据库获取的值
        if (!(value instanceof String)) {
            return value;
        }

        //集合
        if (Collection.class.isAssignableFrom(returnType) || returnType.newInstance() instanceof Collection) {
            return JSONArray.parseArray((String) value, returnType);
        }
        return JSON.parseObject((String) value, returnType);
    }

    public Object getData(ProceedingJoinPoint jp, String cacheKey) throws Throwable {
        Lock lock = redissonClient.getLock(cacheKey + ".lock");

        //加分布式锁
        lock.lock();
        try {
            //再次判断缓存中是否有值
            String cacheValue = redisService.get(cacheKey);
            if (cacheValue == null) {
                Object value = jp.proceed();
                if (value == null) {
                    //缓存不存在，放0,防止缓存穿透
                    redisService.set(cacheKey, CacheConstants.CACHE_NOT_EXIST, 30 * 60);
                } else {
                    redisService.set(cacheKey, CacheUtils.toJsonString(value), 30 * 60);
                }
                return value;
            }
            return cacheValue;

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
