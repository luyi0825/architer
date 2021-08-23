package com.architecture.context.cache.operation;


import com.architecture.context.cache.model.InvalidCache;
import com.architecture.context.cache.proxy.MethodReturnValueFunction;
import com.architecture.context.cache.utils.CacheUtils;
import com.architecture.context.expression.ExpressionMetadata;

import java.util.List;


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
            long expireTime = CacheUtils.getExpireTime(cacheableOperation.getExpireTime(), cacheableOperation.getRandomTime());
            cacheValue = methodReturnValueFunction.proceed();
            for (String cacheKey : cacheKeys) {
                cacheService.set(cacheKey, cacheValue, expireTime, cacheableOperation.getExpireTimeUnit());
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
