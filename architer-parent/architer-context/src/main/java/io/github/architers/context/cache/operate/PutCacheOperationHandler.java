package io.github.architers.context.cache.operate;


import io.github.architers.context.cache.utils.CacheUtils;
import io.github.architers.context.cache.annotation.PutCache;
import io.github.architers.context.cache.model.PutParam;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;
import io.github.architers.context.utils.JsonUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;

/**
 * 对应PutCacheOperation的handler,先调用返回结果，后put.
 * <li>默认使用方法的返回值作为缓存，如果指定了使用了缓存值就用指定的缓存值</li>
 * <li>支持批量设置缓存</li>
 * <li>支持锁</li>
 *
 * @author luyi
 * @version 1.0.0
 */
public class PutCacheOperationHandler extends BaseCacheOperationHandler {

    private static final int SECOND_ORDER = 2;

    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof PutCache;
    }

    @Override
    protected void executeCacheOperate(Annotation operationAnnotation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        PutCache putCache = (PutCache) operationAnnotation;

        if (!this.canDoCacheOperate(putCache.condition(), putCache.unless(), expressionMetadata)) {
            //调用方法
            methodReturnValueFunction.proceed();
            return;
        }
        //默认为方法的返回值，当设置了缓存值就用指定的缓存值
        //得到过期时间
        long expireTime = CacheUtils.getExpireTime(putCache.expireTime(), putCache.randomTime());

        Object key = parseCacheKey(expressionMetadata, putCache.key());
        PutParam putParam = new PutParam();
        putParam.setWrapperCacheName(super.getWrapperCacheName(putCache.cacheName(), expressionMetadata));
        putParam.setOriginCacheName(putCache.cacheName());
        putParam.setKey(JsonUtils.toJsonString(key));

        putParam.setExpireTime(expireTime);
        putParam.setTimeUnit(putCache.timeUnit());
        CacheOperate cacheOperate = super.cacheOperateSupport.getCacheOperate(putCache.cacheName());
        if (!CollectionUtils.isEmpty(cacheOperateHooks)) {
            for (CacheOperateHook cacheOperateHook : cacheOperateHooks) {
                if (!cacheOperateHook.before(putParam, cacheOperate)) {
                    //终止操作也调用方法
                    methodReturnValueFunction.proceed();
                    return;
                }
            }
        }
        //调用方法
        methodReturnValueFunction.proceed();
        Object cacheValue = expressionParser.parserExpression(expressionMetadata, putCache.cacheValue());
        putParam.setCacheValue(cacheValue);

        cacheOperate.put(putParam);
        if (!CollectionUtils.isEmpty(cacheOperateHooks)) {
            for (CacheOperateHook cacheOperateHook : cacheOperateHooks) {
                cacheOperateHook.end(putParam, cacheOperate);
            }
        }


    }

}
