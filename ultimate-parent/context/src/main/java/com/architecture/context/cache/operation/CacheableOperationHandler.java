package com.architecture.context.cache.operation;


import com.architecture.context.cache.model.InvalidCache;
import com.architecture.context.cache.proxy.ReturnValueFunction;
import com.architecture.context.cache.utils.CacheUtils;
import com.architecture.context.expression.ExpressionMetadata;

import java.util.List;


/**
 * CacheableOperation 对应的处理类
 *
 * @author luyi
 */
public class CacheableOperationHandler extends CacheOperationHandler {


    @Override
    public boolean match(CacheOperation operation) {
        return operation instanceof CacheableOperation;
    }

    @Override
    protected void execute(CacheOperation operation, ExpressionMetadata expressionMetadata, ReturnValueFunction returnValueFunction) throws Throwable {
        CacheableOperation cacheableOperation = (CacheableOperation) operation;
        List<String> cacheKeys = getCacheKeys(cacheableOperation, expressionMetadata);
        List<Object> values = cacheService.multiGet(cacheKeys);
        Object cacheValue = null;
        for (Object value : values) {
            if (!isNullValue(value)) {
                cacheValue = value;
                break;
            }
        }
        if (cacheValue == null) {
            cacheValue = returnValueFunction.proceed();
            long expireTime = CacheUtils.getExpireTime(cacheableOperation.getExpireTime(), cacheableOperation.getRandomTime());
            for (String cacheKey : cacheKeys) {
                cacheService.set(cacheKey, cacheValue, expireTime, cacheableOperation.getExpireTimeUnit());
            }
        } else {
            returnValueFunction.setValue(cacheValue);
        }
    }

    private boolean isNullValue(Object value) {
        return value == null || value instanceof InvalidCache;
    }
}
