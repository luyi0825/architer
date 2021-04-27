package com.lz.core.cache.common.operation;

import com.lz.core.cache.common.annotation.DeleteCache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 对应DeleteCacheOperation
 * @author luyi
 */
public class DeleteCacheOperationHandler extends CacheOperationHandler{
    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof DeleteCache;
    }

    @Override
    protected Object executeCacheHandler(String key, Object target, Method method, Object[] args, CacheOperation operation) {
       Object  value = invoke(target, method, args);
       cacheManager.deleteCache(key);
       return value;
    }
}
