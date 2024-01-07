package io.github.architers.context.lock.proxy;

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
public class AnnotationLockAspect {


    private final LockProxySupport lockProxySupport;

    public AnnotationLockAspect(LockProxySupport lockProxySupport) {
        this.lockProxySupport = lockProxySupport;
    }

    @Around("@annotation(io.github.architers.context.lock.annotation.ExclusiveLock)")
    public Object cachePut(ProceedingJoinPoint pjp) throws Throwable {
        return this.executeCacheProxy(pjp);
    }


    @Around("@annotation(io.github.architers.context.lock.annotation.ReadWriteLock)")
    public Object cacheBatchEvict(ProceedingJoinPoint pjp) throws Throwable {
        return this.executeCacheProxy(pjp);
    }

    private Object executeCacheProxy(ProceedingJoinPoint pjp) throws Throwable {
        ExpressionMetadata metadata = this.buildExpressionMeta(pjp);
        return lockProxySupport.executeProxy(metadata, pjp::proceed);
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
