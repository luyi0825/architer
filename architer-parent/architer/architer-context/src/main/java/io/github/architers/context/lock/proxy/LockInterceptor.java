package io.github.architers.context.lock.proxy;


import io.github.architers.common.expression.method.ExpressionMetadata;
import io.github.architers.common.expression.method.ExpressionParser;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * @author luyi
 * @version 1.0.0
 * 缓存拦截器：解析缓存、缓存锁相关的注解，进行相关的处理
 */
public class LockInterceptor implements MethodInterceptor {



    private final LockProxySupport lockProxySupport;

    public LockInterceptor(LockProxySupport lockProxySupport) {
        this.lockProxySupport = lockProxySupport;
    }

    @Override
    @Nullable
    public Object invoke(@NonNull final MethodInvocation invocation) throws Throwable {
        ExpressionMetadata expressionMetadata = this.buildExpressionMeta(invocation);
        return lockProxySupport.executeProxy(expressionMetadata, () -> {
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
