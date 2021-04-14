package com.lz.core.cache.common.aspectj;

import com.lz.core.cache.common.CacheProcess;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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
     * 缓存caching切点
     */
    @Pointcut("@annotation(com.lz.core.cache.common.annotation.Caching)")
    public void cachingPointcut() {
    }

    /**
     * 拦截缓存注解
     *
     * @param jp 切点信息
     * @return 缓存数据
     */
    @Around("cachingPointcut()")
    public Object caching(ProceedingJoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        Object target = jp.getTarget();
        return cacheProcess.process(target, method, jp.getArgs());
    }


}
