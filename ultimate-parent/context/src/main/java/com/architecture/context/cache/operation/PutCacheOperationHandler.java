package com.architecture.context.cache.operation;


import com.architecture.context.cache.proxy.MethodReturnValueFunction;
import com.architecture.context.cache.utils.CacheUtils;
import com.architecture.context.expression.ExpressionMetadata;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 对应PutCacheOperation
 *
 * @author luyi
 */
public class PutCacheOperationHandler extends CacheOperationHandler {

    private static final int SECOND_ORDER = 2;

    @Override
    public boolean match(BaseCacheOperation operation) {
        return operation instanceof PutCacheOperation;
    }

    @Override
    protected void execute(BaseCacheOperation operation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        PutCacheOperation putCacheOperation = (PutCacheOperation) operation;
        Collection<String> cacheNames = getCacheNames(operation, expressionMetadata);
        long expireTime = CacheUtils.getExpireTime(putCacheOperation.getExpireTime(), putCacheOperation.getRandomTime());
        String cacheValue = putCacheOperation.getCacheValue();
        methodReturnValueFunction.proceed();
        Object value = expressionParser.parserExpression(expressionMetadata, cacheValue);
        if (this.canHandler(operation, expressionMetadata, false)) {
            String key = (String) expressionParser.parserExpression(expressionMetadata, operation.getKey());
            for (String cacheName : cacheNames) {
                cacheManager.getSimpleCache(cacheName).set(key, value, expireTime, TimeUnit.MINUTES);
            }
        }
    }

    @Override
    public int getOrder() {
        return SECOND_ORDER;
    }
}
