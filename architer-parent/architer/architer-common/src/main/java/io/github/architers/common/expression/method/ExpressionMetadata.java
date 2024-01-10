package io.github.architers.common.expression.method;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.MethodBasedEvaluationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luyi
 * 表达式元数据
 */
public class ExpressionMetadata {
    private final Class<?> targetClass;
    private final Method targetMethod;
    private final Object[] args;
    private final Object target;
    private final AnnotatedElementKey methodKey;
    private MethodBasedEvaluationContext expressionEvaluationContext;
    private final Map<String, Object> variables = new HashMap<>(2);


    public ExpressionMetadata(Object target, Method method, Object[] args) {
        this.target = target;
        this.targetClass = AopProxyUtils.ultimateTargetClass(target);
        this.targetMethod = (!Proxy.isProxyClass(targetClass) ?
                AopUtils.getMostSpecificMethod(method, targetClass) : method);
        this.args = args;
        this.methodKey = new AnnotatedElementKey(this.targetMethod, targetClass);
    }

    public ExpressionMetadata setEvaluationContext(MethodBasedEvaluationContext expressionEvaluationContext) {
        this.expressionEvaluationContext = expressionEvaluationContext;
        return this;
    }

    public MethodBasedEvaluationContext getEvaluationContext() {
        return expressionEvaluationContext;
    }

    public Map<String, Object> getVariables() {
        return variables;
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
