package io.github.architers.context.cache.proxy;


import io.github.architers.context.cache.CacheAnnotationsParser;
import io.github.architers.context.cache.annotation.CacheBatchEvict;
import io.github.architers.context.cache.annotation.CacheEvict;
import io.github.architers.context.cache.annotation.CacheEvictAll;
import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.model.InvalidCacheValue;
import io.github.architers.context.cache.operate.BaseCacheOperationHandler;
import io.github.architers.context.expression.ExpressionMetadata;
import io.github.architers.context.expression.ExpressionParser;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author luyi
 * @version 1.0.0
 * 缓存拦截器：解析缓存、缓存锁相关的注解，进行相关的处理
 */
public class CacheInterceptor implements MethodInterceptor {

    private CacheAnnotationsParser cacheAnnotationsParser;

    private List<BaseCacheOperationHandler> baseCacheOperationHandlers;

    private final Map<Method, MethodCacheAnnotationContext> methodMethodCacheContextCache = new ConcurrentHashMap<>(32);

    @Override
    @Nullable
    public Object invoke(@NonNull final MethodInvocation invocation) throws Throwable {
        /*
         *构建表达式的元数据:expressionMetadata
         *1.由于对于同一个线程的ExpressionEvaluationContext一样，可能存在多个注解，减少对象的创建
         *2.方便不同注解拓展变量值传递
         */
        ExpressionMetadata expressionMetadata = this.buildExpressionMeta(invocation);

        MethodCacheAnnotationContext methodCacheAnnotationContext = methodMethodCacheContextCache.get(invocation.getMethod());
        if (methodCacheAnnotationContext == null) {
            methodCacheAnnotationContext = this.buildMethodCacheAnnotationContext(invocation.getMethod());
        }
        //返回值构建，也方便多个注解的时候，重复调用方法
        AtomicReference<Object> returnValue = new AtomicReference<>();
        MethodReturnValueFunction methodReturnValueFunction = new MethodReturnValueFunction() {
            /**
             *  调用方法，只有第一次调用是真的调用
             */
            @Override
            public Object proceed() throws Throwable {
                if (returnValue.get() == null) {
                    //执行方法
                    Object value = invocation.proceed();
                    if (value == null) {
                        value = InvalidCacheValue.INVALID_CACHE;
                    }
                    //设置特定值，防止重复调用方法
                    setValue(value);
                }
                return returnValue.get();
            }

            @Override
            public void setValue(Object value) {
                if (value != null && !(value instanceof InvalidCacheValue)) {
                    //支持#result表达式
                    expressionMetadata.getEvaluationContext().setVariable("result", value);
                }
                if (value != null && returnValue.get() == null) {
                    returnValue.set(value);
                }
            }
        };

        //执行调用方法之前的注解操作
        this.executeCacheOperates(methodCacheAnnotationContext.getBeforeInvocations(), expressionMetadata, methodReturnValueFunction);
        //执行cacheable注解
        this.executeCacheOperates(methodCacheAnnotationContext.getCacheables(), expressionMetadata, methodReturnValueFunction);
        //调用方法（如果有cacheable就不会真的调用）
        methodReturnValueFunction.proceed();
        //执行调用方法后的注解操作
        this.executeCacheOperates(methodCacheAnnotationContext.getAfterInvocations(), expressionMetadata, methodReturnValueFunction);

        Object value = returnValue.get();

        //已经调用了方法，缓存中放的空值
        if (value instanceof InvalidCacheValue) {
            return null;
        }
        //获取到返回值
        return value;
    }

    private void executeCacheOperates(Collection<Annotation> operationAnnotations, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        if (CollectionUtils.isEmpty(operationAnnotations)) {
            return;
        }
        for (Annotation operationAnnotation : operationAnnotations) {
            for (BaseCacheOperationHandler baseCacheOperationHandler : baseCacheOperationHandlers) {
                if (baseCacheOperationHandler.match(operationAnnotation)) {
                    baseCacheOperationHandler.handler(operationAnnotation, methodReturnValueFunction, expressionMetadata);
                }
            }
        }
        throw new RuntimeException("没有匹配到CacheOperationHandler");

    }

    private MethodCacheAnnotationContext buildMethodCacheAnnotationContext(Method method) {
        Collection<? extends Annotation> operationAnnotations = cacheAnnotationsParser.parse(method);

        MethodCacheAnnotationContext methodCacheAnnotationContext = new MethodCacheAnnotationContext();

        for (Annotation operationAnnotation : operationAnnotations) {
            if (operationAnnotation instanceof CacheEvict) {
                if (((CacheEvict) operationAnnotation).beforeInvocation()) {
                    methodCacheAnnotationContext.addBeforeInvocations(operationAnnotation);
                } else {
                    methodCacheAnnotationContext.addAfterInvocations(operationAnnotation);
                }
            } else if (operationAnnotation instanceof CacheBatchEvict) {
                if (((CacheBatchEvict) operationAnnotation).beforeInvocation()) {
                    methodCacheAnnotationContext.addBeforeInvocations(operationAnnotation);
                } else {
                    methodCacheAnnotationContext.addAfterInvocations(operationAnnotation);
                }
            } else if (operationAnnotation instanceof CacheEvictAll) {
                if (((CacheEvictAll) operationAnnotation).beforeInvocation()) {
                    methodCacheAnnotationContext.addBeforeInvocations(operationAnnotation);
                } else {
                    methodCacheAnnotationContext.addAfterInvocations(operationAnnotation);
                }
            } else if (operationAnnotation instanceof Cacheable) {
                methodCacheAnnotationContext.addCacheables(operationAnnotation);
            } else {
                methodCacheAnnotationContext.addAfterInvocations(operationAnnotation);
            }
        }
        return methodMethodCacheContextCache.putIfAbsent(method, methodCacheAnnotationContext);
    }


    /**
     * 构建缓存的表达式元数据
     *
     * @param invocation 方法代理的信息
     * @return 表达式元数据
     */
    private ExpressionMetadata buildExpressionMeta(MethodInvocation invocation) {
        ExpressionMetadata expressionMetadata = new ExpressionMetadata(Objects.requireNonNull(invocation.getThis()), invocation.getMethod(), invocation.getArguments());
        MethodBasedEvaluationContext expressionEvaluationContext = ExpressionParser.createEvaluationContext(expressionMetadata);
        expressionMetadata.setEvaluationContext(expressionEvaluationContext);
        return expressionMetadata;
    }


    public void setCacheAnnotationsParser(CacheAnnotationsParser cacheAnnotationsParser) {
        this.cacheAnnotationsParser = cacheAnnotationsParser;
    }


    public void setCacheOperationHandlers(List<BaseCacheOperationHandler> baseCacheOperationHandlers) {
        this.baseCacheOperationHandlers = baseCacheOperationHandlers;
    }

}
