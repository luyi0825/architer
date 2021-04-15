package com.lz.core.cache.common.aspectj;

import com.lz.core.cache.common.CacheProcess;
import com.lz.core.cache.common.annotation.Cacheable;
import com.lz.core.cache.common.annotation.DeleteCache;
import com.lz.core.cache.common.annotation.PutCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author luyi
 * @date 2020-12-20
 */
@Aspect
@Component
public class CacheAspectj {

    private final CacheProcess cacheProcess;

    public CacheAspectj(CacheProcess cacheProcess) {
        this.cacheProcess = cacheProcess;
    }

    /**
     * caching切点
     */
    @Pointcut("@annotation(com.lz.core.cache.common.annotation.Cacheable)")
    public void cachingPointcut() {
    }

    /**
     * 放置putCache切点
     */
    @Pointcut("@annotation(com.lz.core.cache.common.annotation.PutCache)")
    public void putCachePointcut() {
    }

    /**
     * 删除deleteCache切点
     */
    @Pointcut("@annotation(com.lz.core.cache.common.annotation.DeleteCache)")
    public void deleteCachePointcut() {
    }


    @Around("cachingPointcut()")
    public Object cacheable(ProceedingJoinPoint jp) {
        return handler(jp, Cacheable.class);
    }

    @Around("putCachePointcut()")
    public Object putCaching(ProceedingJoinPoint jp) {
        return handler(jp, PutCache.class);
    }

    @Around("deleteCachePointcut()")
    public Object deleteCache(ProceedingJoinPoint jp) {
        return handler(jp, DeleteCache.class);
    }


    public Object handler(ProceedingJoinPoint proceedingJoinPoint, Class<?> clazz) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object target = proceedingJoinPoint.getTarget();
        return cacheProcess.process(target, method, proceedingJoinPoint.getArgs(), clazz);
    }


}
