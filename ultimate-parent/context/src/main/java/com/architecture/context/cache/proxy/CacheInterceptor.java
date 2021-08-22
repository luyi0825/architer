package com.architecture.context.cache.proxy;


import com.architecture.context.cache.CacheAnnotationsParser;
import com.architecture.context.cache.CacheService;
import com.architecture.context.cache.model.InvalidCache;
import com.architecture.context.cache.operation.CacheOperation;
import com.architecture.context.cache.operation.CacheOperationHandler;
import com.architecture.context.cache.operation.CacheableOperation;
import com.architecture.context.expression.ExpressionMetadata;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author luyi
 * 缓存拦截器
 */
public class CacheInterceptor implements MethodInterceptor {

    private CacheAnnotationsParser cacheAnnotationsParser;

    private List<CacheOperationHandler> cacheOperationHandlers;


    public CacheAnnotationsParser getCacheAnnotationsParser() {
        return cacheAnnotationsParser;
    }

    public List<CacheOperationHandler> getCacheOperationHandlers() {
        return cacheOperationHandlers;
    }

    public CacheInterceptor setCacheOperationHandlers(List<CacheOperationHandler> cacheOperationHandlers) {
        this.cacheOperationHandlers = cacheOperationHandlers;
        return this;
    }

    @Override
    @Nullable
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        Collection<CacheOperation> cacheOperations = cacheAnnotationsParser.parse(invocation.getMethod());
        if (!CollectionUtils.isEmpty(cacheOperations)) {
            return execute(invocation, cacheOperations);
        }
        return invocation.proceed();
    }


    /**
     * 执行拦截的操作
     */
    private Object execute(MethodInvocation invocation, Collection<CacheOperation> cacheOperations) throws Throwable {
        ExpressionMetadata expressionMetadata = new ExpressionMetadata(Objects.requireNonNull(invocation.getThis()), invocation.getMethod(), invocation.getArguments());
        AtomicReference<Object> returnValue = new AtomicReference<>();
        MethodInvocationFunction methodInvocationFunction = new MethodInvocationFunction() {
            @Override
            public synchronized Object proceed() throws Throwable {
                if (returnValue.get() == null) {
                    Object value = invocation.proceed();
                    if (value == null) {
                        value = InvalidCache.INVALID_CACHE;
                    }
                    returnValue.set(value);
                }
                return returnValue.get();
            }
        };
        for (CacheOperation cacheOperation : cacheOperations) {
            if (cacheOperation instanceof CacheableOperation) {
                for (CacheOperationHandler cacheOperationHandler : cacheOperationHandlers) {
                    if (cacheOperationHandler.match(cacheOperation)) {
                        cacheOperationHandler.handler(cacheOperation, methodInvocationFunction, expressionMetadata);
                    }
                }
            }
        }
        Object value = returnValue.get();
        if (value == null) {
            return invocation.proceed();
        }
        if (value instanceof InvalidCache) {
            return null;
        }
        return value;

    }


    public void setCacheAnnotationsParser(CacheAnnotationsParser cacheAnnotationsParser) {
        this.cacheAnnotationsParser = cacheAnnotationsParser;
    }
}
