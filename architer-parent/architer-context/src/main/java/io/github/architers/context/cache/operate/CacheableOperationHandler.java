package io.github.architers.context.cache.operate;


import io.github.architers.context.cache.utils.CacheUtils;
import io.github.architers.context.cache.model.NullValue;
import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.model.GetParam;
import io.github.architers.context.cache.model.PutParam;
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
        //判断是或能够进行缓存操作
        if (!canDoCacheOperate(cacheable.condition(), cacheable.unless(), expressionMetadata)) {
            return;
        }
        CacheOperate cacheOperate = super.cacheOperateFactory.getCacheOperate(cacheable.cacheOperate());

        Object key = super.parseCacheKey(expressionMetadata, cacheable.key());
        CacheNameWrapper cacheNameWrapper = cacheNameWrapperFactory.getCacheNameWrapper(cacheable.cacheNameWrapper());
        String cacheName = cacheNameWrapper.getCacheName(expressionMetadata, cacheable.cacheName());
        GetParam getParam = new GetParam();
        getParam.setCacheOperate(cacheOperate);
        //同步：没有值才查询数据库
        getParam.setAsync(false);
        getParam.setCacheName(cacheable.cacheName());
        getParam.setKey(key);
        Object value = cacheOperate.get(getParam);
        if (!isNullValue(value)) {
            cacheValue = value;
        }
        if (cacheValue == null) {
            //调用方法，只要第一次调用是真的调用
            Object returnValue = methodReturnValueFunction.proceed();
            long expireTime = CacheUtils.getExpireTime(cacheable.expireTime(), cacheable.randomTime());
            PutParam putParam = new PutParam();
            putParam.setCacheName(cacheName);
            putParam.setCacheValue(returnValue);
            putParam.setKey(key);
            putParam.setTimeUnit(cacheable.timeUnit());
            putParam.setExpireTime(expireTime);
            cacheOperate.put(putParam);
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
