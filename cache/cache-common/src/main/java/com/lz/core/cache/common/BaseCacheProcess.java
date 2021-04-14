package com.lz.core.cache.common;

import com.lz.core.cache.common.enums.KeyStrategy;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

public abstract class BaseCacheProcess implements CacheProcess {

    private KeyStrategy keyStrategy;


    @Override
    public String getCacheKey(Method method, Object... args) {
        String key = method.getDeclaringClass().getName() + " [" + StringUtils.arrayToCommaDelimitedString(args) + "]";
        System.out.println(key);
        return key;
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
