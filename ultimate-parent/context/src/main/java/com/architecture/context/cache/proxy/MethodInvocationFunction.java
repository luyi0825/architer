package com.architecture.context.cache.proxy;

public interface MethodInvocationFunction {
    default Object proceed() throws Throwable {
        return null;
    }
}
