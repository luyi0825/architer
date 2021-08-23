package com.architecture.context.expression;


import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 缓存解析的context
 *
 * @author luyi
 */
public class EvaluationContext extends MethodBasedEvaluationContext {
    private final Set<String> unavailableVariables = new HashSet<>(1);

    public EvaluationContext(Object rootObject, Method method, Object[] arguments, ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
    }

    public void addUnavailableVariable(String name) {
        this.unavailableVariables.add(name);
    }


    /**
     * Load the param information only when needed.
     */
    @Override
    @Nullable
    public Object lookupVariable(String name) {
        if (this.unavailableVariables.contains(name)) {

        }
        return super.lookupVariable(name);
    }

}
