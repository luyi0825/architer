package com.lz.core.cache.common.proxy;

import com.lz.core.cache.common.CacheProcess;
import com.lz.core.cache.common.annotation.Cacheable;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author zhoupei
 * @create 2021/4/18
 **/
public class CachingInterceptor implements MethodInterceptor {
    private CacheProcess cacheProcess;

    public CachingInterceptor() {
    }

    public CachingInterceptor(CacheProcess cacheProcess) {
        this.cacheProcess = cacheProcess;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();

        return cacheProcess.process(invocation.getThis(),
                method,
                invocation.getArguments(),
                method.getAnnotation(Cacheable.class).getClass());
    }
}
