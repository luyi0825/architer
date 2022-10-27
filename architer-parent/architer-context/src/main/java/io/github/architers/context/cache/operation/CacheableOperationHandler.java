package io.github.architers.context.cache.operation;


import io.github.architers.context.cache.CacheUtils;
import io.github.architers.context.NullValue;
import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;

import java.lang.annotation.Annotation;


/**
 * CacheableOperation 对应的处理类
 * 当缓存中没有值的时候，查询数据库，并将返回值放入缓存
 *
 * @author luyi
 * @version 1.0.0
 */
public class CacheableOperationHandler extends CacheOperationHandler {


    @Override
    public boolean match(Annotation annotation) {
        return annotation instanceof Cacheable;
    }

    @Override
    protected void execute(Annotation operationAnnotation, ExpressionMetadata expressionMetadata,
                           MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        Cacheable cacheable = (Cacheable) operationAnnotation;

        Object cacheValue = null;

        CacheOperate cacheOperate = super.cacheOperateFactory.getCacheOperate(cacheable.cacheOperate());

        Object key = super.parseCacheKey(expressionMetadata, cacheable.key());
        String cacheName = super.keyGeneratorFactory.getKeyGenerator(cacheable.keyGenerator())
                .generator(expressionMetadata, cacheable.cacheName());

        GetCacheParam getCacheParam = new GetCacheParam();
        getCacheParam.setCacheOperate(cacheOperate);
        //同步：没有值才查询数据库
        getCacheParam.setAsync(false);
        getCacheParam.setCacheName(cacheable.cacheName());
        getCacheParam.setKey(key);
        Object value = cacheOperate.get(getCacheParam);
        if (!isNullValue(value)) {
            cacheValue = value;
        }
        if (cacheValue == null) {
            //调用方法，只要第一次调用是真的调用
            Object returnValue = methodReturnValueFunction.proceed();
            long expireTime = CacheUtils.getExpireTime(cacheable.expireTime(), cacheable.randomTime());
            PutCacheParam putCacheParam = new PutCacheParam();
            putCacheParam.setCacheName(cacheName);
            putCacheParam.setCacheValue(returnValue);
            putCacheParam.setKey(key);
            putCacheParam.setTimeUnit(cacheable.timeUnit());
            putCacheParam.setExpireTime(expireTime);
            cacheOperate.put(putCacheParam);
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

}
