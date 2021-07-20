package com.architecture.ultimate.cache.common.operation;

import lombok.Data;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author luyi
 */
@Data
public class CacheOperationMetadata {
    private final CacheOperation cacheOperation;
    private final Class<?> targetClass;
    private final Method targetMethod;
    private final Object[] args;
    private final Object target;
    private final AnnotatedElementKey methodKey;

    public CacheOperationMetadata(CacheOperation cacheOperation, Object target, Method method, Object[] args) {
        this.cacheOperation = cacheOperation;
        this.target = target;
        this.targetClass = target.getClass();
        this.targetMethod = (!Proxy.isProxyClass(targetClass) ?
                AopUtils.getMostSpecificMethod(method, targetClass) : method);
        this.args = args;
        this.methodKey = new AnnotatedElementKey(this.targetMethod, targetClass);
    }

    public CacheOperation getCacheOperation() {
        return cacheOperation;
    }


    public Class<?> getTargetClass() {
        return targetClass;
    }


    public Method getTargetMethod() {
        return targetMethod;
    }


    public Object[] getArgs() {
        return args;
    }


    public AnnotatedElementKey getMethodKey() {
        return methodKey;
    }
}
