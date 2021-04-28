package com.lz.core.cache.common.aspectj;

import com.lz.core.cache.common.CacheAnnotationsParser;
import com.lz.core.cache.common.CacheProcess;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author luyi
 * @date 2020-12-20
 * @TODO 判断多个缓存注解会怎么样
 */
@Aspect
@Component
public class CacheAspectj {

    private final CacheProcess cacheProcess;

    private final CacheAnnotationsParser cacheAnnotationsParser;

    public CacheAspectj(CacheProcess cacheProcess, CacheAnnotationsParser cacheAnnotationsParser) {
        this.cacheProcess = cacheProcess;
        this.cacheAnnotationsParser = cacheAnnotationsParser;
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
        return handler(jp);
    }

    @Around("putCachePointcut()")
    public Object putCaching(ProceedingJoinPoint jp) {
        return handler(jp);
    }

    @Around("deleteCachePointcut()")
    public Object deleteCache(ProceedingJoinPoint jp) {
        return handler(jp);
    }


    public Object handler(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object target = proceedingJoinPoint.getTarget();
        return cacheProcess.process(target, method, proceedingJoinPoint.getArgs(), cacheAnnotationsParser.parse(method));
    }


}
