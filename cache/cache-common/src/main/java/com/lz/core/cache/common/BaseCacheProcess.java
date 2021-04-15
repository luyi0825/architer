package com.lz.core.cache.common;

import com.lz.core.cache.common.key.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * @author luyi
 */
public abstract class BaseCacheProcess implements CacheProcess {

    private KeyGenerator keyGenerator;

    @Override
    public String getCacheKey(Object target, Method method, Object[] args, Class<?> annotation) {
        return keyGenerator.getKey(target, method, args, annotation);
    }

    @Override
    public Object process(Object target, Method method, Object[] args, Class<?> annotation) {
        try {
            return method.invoke(target, args);
        } catch (Exception e) {
            throw new RuntimeException("调用缓存方法失败", e);
        }
    }

    @Autowired
    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    //    class ProcessParams{
//        private Object target;
//        private Method method;
//        private Object[] args;
//
//
//    }
}
