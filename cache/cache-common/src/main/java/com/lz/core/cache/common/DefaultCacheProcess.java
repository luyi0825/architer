package com.lz.core.cache.common;

import com.lz.core.cache.common.enums.KeyStrategy;

import java.lang.reflect.Method;

public class DefaultCacheProcess implements CacheProcess {

    private KeyStrategy keyStrategy;



    @Override
    public String getCacheKey(Method method, Object... args) {
        return null;
    }

    @Override
    public Object process(Object target, Method method, Object... args) {
        try {
            return method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException("调用缓存方法失败", e);
        }
    }
}
