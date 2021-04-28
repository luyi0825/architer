package com.lz.core.cache.common.operation;

import com.lz.core.cache.common.annotation.DeleteCache;

import java.lang.annotation.Annotation;


/**
 * 对应DeleteCacheOperation
 *
 * @author luyi
 */
public class DeleteCacheOperationHandler extends CacheOperationHandler {
    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof DeleteCache;
    }

    @Override
    protected Object executeCacheHandler(String key, CacheOperationMetadata metadata) {
        Object value = invoke(metadata);
        cacheManager.deleteCache(key);
        return value;
    }
}
