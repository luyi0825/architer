package com.architecture.context.expression;

import com.architecture.context.cache.operation.CacheOperation;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author luyi
 */
public class ExpressionMetadata {
    private final Class<?> targetClass;
    private final Method targetMethod;
    private final Object[] args;
    private final Object target;
    private final AnnotatedElementKey methodKey;

    public ExpressionMetadata(Object target, Method method, Object[] args) {
        ;
        this.target = target;
        this.targetClass = target.getClass();
        this.targetMethod = (!Proxy.isProxyClass(targetClass) ?
                AopUtils.getMostSpecificMethod(method, targetClass) : method);
        this.args = args;
        this.methodKey = new AnnotatedElementKey(this.targetMethod, targetClass);
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

    public Object getTarget() {
        return target;
    }
}
