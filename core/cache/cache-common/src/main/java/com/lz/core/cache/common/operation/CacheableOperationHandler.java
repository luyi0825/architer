package com.lz.core.cache.common.operation;


import com.lz.core.cache.common.annotation.Cacheable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * CacheableOperation 对应的处理类
 *
 * @author luyi
 */
public class CacheableOperationHandler extends CacheOperationHandler {

    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof Cacheable;
    }

    @Override
    protected Object executeCacheHandler(String key, Object target, Method method, Object[] args, CacheOperation operation) {
        Object value = cacheManager.getCache(key);
        if (value == null) {
            value = invoke(target, method, args);
            long expireTime = getKeyExpireTime(operation);
            value = cacheManager.putCache(key, value, expireTime);
        }
        return value;
    }

    /**
     * 得到key过期时间
     *
     * @param operation CacheableOperation
     * @return 过期的时间
     */
    private Long getKeyExpireTime(CacheOperation operation) {
        CacheableOperation cacheableOperation = (CacheableOperation) operation;
        return KeyExpireUtils.getExpireTime(cacheableOperation.getExpireTime(), cacheableOperation.getRandomExpireTime());
    }
}
