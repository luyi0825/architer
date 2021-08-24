package com.architecture.context.cache.operation;


import com.architecture.context.cache.Cache;
import com.architecture.context.cache.model.InvalidCache;
import com.architecture.context.cache.proxy.MethodReturnValueFunction;
import com.architecture.context.cache.utils.CacheUtils;
import com.architecture.context.expression.ExpressionMetadata;

import java.util.*;


/**
 * CacheableOperation 对应的处理类
 *
 * @author luyi
 */
public class CacheableOperationHandler extends CacheOperationHandler {

    private static final int FIRST_ORDER = 1;

    @Override
    public boolean match(BaseCacheOperation operation) {
        return operation instanceof CacheableOperation;
    }

    @Override
    protected void execute(BaseCacheOperation operation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {

        CacheableOperation cacheableOperation = (CacheableOperation) operation;
        Collection<String> cacheNames = getCacheNames(cacheableOperation, expressionMetadata);
        String key = Objects.requireNonNull(expressionParser.parserExpression(expressionMetadata, operation.getKey())).toString();
        Object cacheValue = null;
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getSimpleCache(cacheName);
            Object value = cache.get(key);
            if (!isNullValue(value)) {
                cacheValue = value;
                break;
            }
        }
        if (cacheValue == null) {
            long expireTime = CacheUtils.getExpireTime(cacheableOperation.getExpireTime(), cacheableOperation.getRandomTime());
            cacheValue = methodReturnValueFunction.proceed();
            for (String cacheName : cacheNames) {
                Cache cache = cacheManager.getSimpleCache(cacheName);
                cache.set(key, cacheValue, expireTime, ((CacheableOperation) operation).getExpireTimeUnit());
            }
        } else {
            methodReturnValueFunction.setValue(cacheValue);
        }
    }

    private boolean isNullValue(Object value) {
        return value == null || value instanceof InvalidCache;
    }

    @Override
    public int getOrder() {
        return FIRST_ORDER;
    }
}
