package com.core.cache.common.proxy;

import com.core.cache.common.operation.CacheOperation;
import com.core.cache.common.CacheProcess;
import com.core.cache.common.CacheAnnotationsParser;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author luyi
 * 缓存拦截器
 */
public class CacheInterceptor implements MethodInterceptor {

    private CacheAnnotationsParser cacheAnnotationsParser;


    private CacheProcess cacheProcess;


    @Override
    @Nullable
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        return this.execute(invocation);
    }

    /**
     * 执行拦截的操作
     */
    private Object execute(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Collection<CacheOperation> cacheOperations = cacheAnnotationsParser.parse(method);
        if (!CollectionUtils.isEmpty(cacheOperations)) {
            return cacheProcess.process(invocation.getThis(), method, invocation.getArguments(), cacheOperations);
        }
        return invocation.proceed();
    }


    public void setCacheProcess(CacheProcess cacheProcess) {
        this.cacheProcess = cacheProcess;
    }

    public void setCacheAnnotationsParser(CacheAnnotationsParser cacheAnnotationsParser) {
        this.cacheAnnotationsParser = cacheAnnotationsParser;
    }
}
