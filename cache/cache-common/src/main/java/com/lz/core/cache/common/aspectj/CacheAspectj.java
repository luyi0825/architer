package com.lz.core.cache.common.aspectj;

import com.lz.core.cache.common.CacheProcess;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author luyi
 * @date 2020-12-20
 */
@Aspect
public class CacheAspectj {

    private final CacheProcess cacheProcess;

    public CacheAspectj(CacheProcess cacheProcess) {
        this.cacheProcess = cacheProcess;
    }

    /**
     * 拦截缓存注解
     *
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around(value = "@annotation(com.lz.core.cache.anntion.Caching)")
    public Object cached(ProceedingJoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        Object target = jp.getTarget();
        return cacheProcess.process(target, method, jp.getArgs());
    }


}
