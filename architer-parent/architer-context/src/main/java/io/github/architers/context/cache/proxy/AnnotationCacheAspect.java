package io.github.architers.context.cache.proxy;

import io.github.architers.common.expression.method.ExpressionMetadata;
import io.github.architers.common.expression.method.ExpressionParser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;

import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
public class AnnotationCacheAspect {


    private final CacheProxySupport cacheProxySupport;

    public AnnotationCacheAspect(CacheProxySupport cacheProxySupport) {
        this.cacheProxySupport = cacheProxySupport;
    }

    @Around("@annotation(io.github.architers.context.cache.annotation.CachePut)")
    public Object cachePut(ProceedingJoinPoint pjp) throws Throwable {
        return this.executeCacheProxy(pjp);
    }

    @Around("@annotation(io.github.architers.context.cache.annotation.CacheBatchPut)")
    public Object cacheBatchPut(ProceedingJoinPoint pjp) throws Throwable {
        return this.executeCacheProxy(pjp);
    }

    @Around("@annotation(io.github.architers.context.cache.annotation.Cacheable)")
    public Object cacheable(ProceedingJoinPoint pjp) throws Throwable {
        return this.executeCacheProxy(pjp);
    }

    @Around("@annotation(io.github.architers.context.cache.annotation.CacheEvict)")
    public Object cacheEvict(ProceedingJoinPoint pjp) throws Throwable {
        return this.executeCacheProxy(pjp);
    }

    @Around("@annotation(io.github.architers.context.cache.annotation.CacheBatchEvict)")
    public Object cacheBatchEvict(ProceedingJoinPoint pjp) throws Throwable {
        return this.executeCacheProxy(pjp);
    }

    private Object executeCacheProxy(ProceedingJoinPoint pjp) throws Throwable {
        ExpressionMetadata metadata = this.buildExpressionMeta(pjp);
        return cacheProxySupport.executeProxy(metadata, () -> {
            try {
                return pjp.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }


    /**
     * 构建缓存的表达式元数据
     *
     * @return 表达式元数据
     */
    private ExpressionMetadata buildExpressionMeta(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        ExpressionMetadata expressionMetadata = new ExpressionMetadata(Objects.requireNonNull(pjp.getTarget()), method, pjp.getArgs());
        MethodBasedEvaluationContext expressionEvaluationContext = ExpressionParser.createEvaluationContext(expressionMetadata);
        expressionMetadata.setEvaluationContext(expressionEvaluationContext);
        return expressionMetadata;
    }


}
