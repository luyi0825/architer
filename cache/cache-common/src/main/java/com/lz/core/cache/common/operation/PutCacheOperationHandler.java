package com.lz.core.cache.common.operation;

import com.lz.core.cache.common.Constants;
import com.lz.core.cache.common.annotation.PutCache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 对应PutCacheOperation
 *
 * @author luyi
 */
public class PutCacheOperationHandler extends CacheOperationHandler {
    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof PutCache;
    }

    @Override
    protected Object executeCacheHandler(String key, Object target, Method method, Object[] args, CacheOperation operation) {
        Object value = invoke(target, method, args);
        cacheManager.putCache(key, value, getKeyExpireTime(operation));
        return value;
    }

    /**
     * 得到key过期时间
     *
     * @param operation PutCacheOperation
     * @return 过期的时间
     */
    private Long getKeyExpireTime(CacheOperation operation) {
        PutCacheOperation putCacheOperation = (PutCacheOperation) operation;
        return KeyExpireUtils.getExpireTime(putCacheOperation.getExpireTime(), putCacheOperation.getRandomExpireTime());
    }
}
