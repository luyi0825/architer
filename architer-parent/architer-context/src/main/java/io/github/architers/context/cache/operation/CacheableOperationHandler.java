package io.github.architers.context.cache.operation;


import io.github.architers.context.cache.Cache;
import io.github.architers.context.cache.CacheUtils;
import io.github.architers.context.cache.model.NullValue;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;

import java.util.Objects;


/**
 * CacheableOperation 对应的处理类
 * 当缓存中没有值的时候，查询数据库，并将返回值放入缓存
 *
 * @author luyi
 * @version 1.0.0
 */
public class CacheableOperationHandler extends CacheOperationHandler {

    private static final int FIRST_ORDER = 1;

    @Override
    public boolean match(CacheOperation cacheOperation) {
        return cacheOperation instanceof CacheableCacheOperation;
    }

    @Override
    protected void execute(BaseCacheOperation operation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        CacheableCacheOperation cacheableOperation = (CacheableCacheOperation) operation;
        String key = Objects.requireNonNull(expressionParser.parserExpression(expressionMetadata, operation.getKey())).toString();
        Object cacheValue = null;
        for (String cacheName : operation.getCacheName()) {
            Cache cache = chooseCache(operation, cacheName);
            Object value = cache.get(key);
            if (!isNullValue(value)) {
                cacheValue = value;
                break;
            }
        }
        if (cacheValue == null) {

                long expireTime = CacheUtils.getExpireTime(cacheableOperation.getExpireTime(), cacheableOperation.getRandomTime());
                Object returnValue = methodReturnValueFunction.proceed();
                for (String cacheName : operation.getCacheName()) {
                    Cache cache = chooseCache(operation, cacheName);
                    cache.set(key, returnValue, expireTime,
                            ((CacheableCacheOperation) operation).getTimeUnit());
                }


        } else {
            //设置返回值，防止重复调用
            methodReturnValueFunction.setValue(cacheValue);
        }
    }

    /**
     * 判断是否是空值
     *
     * @param value 缓存值
     * @return true表示为空值
     */
    private boolean isNullValue(Object value) {
        return value == null || value instanceof NullValue;
    }

    @Override
    public int getOrder() {
        return FIRST_ORDER;
    }
}
