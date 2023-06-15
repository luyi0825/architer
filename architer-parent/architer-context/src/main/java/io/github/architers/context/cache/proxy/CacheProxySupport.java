package io.github.architers.context.cache.proxy;


import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.model.InvalidCacheValue;
import io.github.architers.context.cache.operate.BaseCacheOperationHandler;
import io.github.architers.context.expression.ExpressionMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * 缓存代理支持
 * <li>负责aspectJ和proxy的代理执行</li>
 * <li>管理缓存操作处理器的执行</li>
 */
public class CacheProxySupport {

    private CacheAnnotationsParser cacheAnnotationsParser;

    private List<BaseCacheOperationHandler> baseCacheOperationHandlers;

    private final Map<Method, MethodCacheAnnotationContext> methodMethodCacheContextCache = new ConcurrentHashMap<>(32);

    public Object executeProxy(ExpressionMetadata expressionMetadata, Supplier<Object> methodResultSupplier) throws Throwable {
        MethodCacheAnnotationContext methodCacheAnnotationContext = methodMethodCacheContextCache.get(expressionMetadata.getTargetMethod());
        if (methodCacheAnnotationContext == null) {
            methodCacheAnnotationContext = this.buildMethodCacheAnnotationContext(expressionMetadata.getTargetMethod());
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
                    Object value = methodResultSupplier.get();
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
                    return;
                }
            }
        }
        throw new RuntimeException("没有匹配到CacheOperationHandler");

    }

    private MethodCacheAnnotationContext buildMethodCacheAnnotationContext(Method method) {
        Collection<? extends Annotation> operationAnnotations = cacheAnnotationsParser.parse(method);

        MethodCacheAnnotationContext methodCacheAnnotationContext = new MethodCacheAnnotationContext();

        for (Annotation operationAnnotation : operationAnnotations) {
            if (operationAnnotation instanceof Cacheable) {
                methodCacheAnnotationContext.addCacheables(operationAnnotation);
            } else {
                Boolean beforeInvocation = (Boolean) AnnotationUtils.getValue(operationAnnotation, "beforeInvocation");
                if (Boolean.TRUE.equals(beforeInvocation)) {
                    methodCacheAnnotationContext.addBeforeInvocations(operationAnnotation);
                } else {
                    methodCacheAnnotationContext.addAfterInvocations(operationAnnotation);
                }

            }
        }
        methodMethodCacheContextCache.putIfAbsent(method, methodCacheAnnotationContext);
        return methodCacheAnnotationContext;
    }

    @Autowired(required = false)
    public void setCacheAnnotationsParser(CacheAnnotationsParser cacheAnnotationsParser) {
        this.cacheAnnotationsParser = cacheAnnotationsParser;
    }


    @Autowired(required = false)
    public void setCacheOperationHandlers(List<BaseCacheOperationHandler> baseCacheOperationHandlers) {
        this.baseCacheOperationHandlers = baseCacheOperationHandlers;
    }
}
