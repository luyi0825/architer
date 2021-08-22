package com.architecture.context.cache.proxy;

public interface ReturnValueFunction {
    default Object proceed() throws Throwable {
        return null;
    }

    void setValue(Object value);
}
