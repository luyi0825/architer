package io.github.architers.context.cache.proxy;


import io.github.architers.context.cache.CacheAnnotationsParser;
import io.github.architers.context.cache.model.NullValue;
import io.github.architers.context.cache.operate.CacheOperationHandler;
import io.github.architers.context.expression.ExpressionMetadata;
import io.github.architers.context.expression.ExpressionParser;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author luyi
 * @version 1.0.0
 * 缓存拦截器：解析缓存、缓存锁相关的注解，进行相关的处理
 */
public class CacheInterceptor implements MethodInterceptor {

    private CacheAnnotationsParser cacheAnnotationsParser;

    private List<CacheOperationHandler> cacheOperationHandlers;

    @Override
    @Nullable
    public Object invoke(@NonNull final MethodInvocation invocation) throws Throwable {
        /*
         *构建表达式的元数据:expressionMetadata
         *1.由于对于同一个线程的ExpressionEvaluationContext一样，可能存在多个注解，再次构建，减少对象的创建
         *2.方便不同注解拓展变量值传递
         */
        ExpressionMetadata expressionMetadata = this.buildExpressionMeta(invocation);

        Collection<? extends Annotation> operationAnnotations =
                cacheAnnotationsParser.parse(invocation.getMethod());
        if (!CollectionUtils.isEmpty(operationAnnotations)) {
            Object returnValue = execute(invocation, operationAnnotations, expressionMetadata);
            //已经调用了方法，缓存中放的空值
            if (returnValue instanceof NullValue) {
                return null;
            }
            //获取到返回值
            if (returnValue != null) {
                return returnValue;
            }
            //没有调用过方法，调用一次(
            return invocation.proceed();
        }
        return invocation.proceed();
    }

    /**
     * 执行拦截的操作
     */
    private Object execute(MethodInvocation invocation, Collection<? extends Annotation> operationAnnotations, ExpressionMetadata expressionMetadata) throws Throwable {
        //返回值构建，也方便多个注解的时候，重复调用方法
        AtomicReference<Object> returnValue = new AtomicReference<>();
        MethodReturnValueFunction methodReturnValueFunction = new MethodReturnValueFunction() {
            @Override
            public Object proceed() throws Throwable {
                //TODO 是否要用锁
                //  synchronized (this) {
                if (returnValue.get() == null) {
                    //执行方法
                    Object value = invocation.proceed();
                    if (value == null) {
                        value = NullValue.INVALID_CACHE;
                    }
                    //设置特定值，防止重复调用方法
                    setValue(value);
                }
                return returnValue.get();
                //  }
            }

            @Override
            public void setValue(Object value) {
                // synchronized (this) {
                if (value != null && !(value instanceof NullValue)) {
                    //支持#result表达式
                    expressionMetadata.getEvaluationContext().setVariable("result", value);
                }
                if (value != null && returnValue.get() == null) {
                    returnValue.set(value);
                }
                //  }
            }
        };
        for (Annotation operationAnnotation : operationAnnotations) {
            for (CacheOperationHandler cacheOperationHandler : cacheOperationHandlers) {
                if (cacheOperationHandler.match(operationAnnotation)) {
                    cacheOperationHandler.handler(operationAnnotation, methodReturnValueFunction, expressionMetadata);
                    break;
                }
            }
        }
        return returnValue.get();
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


    public void setCacheOperationHandlers(List<CacheOperationHandler> cacheOperationHandlers) {
        this.cacheOperationHandlers = cacheOperationHandlers;
    }

}
