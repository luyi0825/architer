package io.github.architers.context.cache.proxy;


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
import java.util.function.Supplier;

/**
 * @author luyi
 * @version 1.0.0
 * 缓存拦截器：解析缓存、缓存锁相关的注解，进行相关的处理
 */
public class CacheInterceptor implements MethodInterceptor {


    private final CacheProxySupport cacheProxySupport;

    public CacheInterceptor(CacheProxySupport cacheProxySupport) {
        this.cacheProxySupport = cacheProxySupport;
    }

    @Override
    @Nullable
    public Object invoke(@NonNull final MethodInvocation invocation) throws Throwable {
        ExpressionMetadata expressionMetadata = this.buildExpressionMeta(invocation);
        return cacheProxySupport.excuteProxy(expressionMetadata, () -> {
            try {
                return invocation.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });

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


}
