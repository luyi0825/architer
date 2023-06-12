package io.github.architers.context.cache.operate;


import io.github.architers.context.cache.utils.CacheUtils;
import io.github.architers.context.cache.annotation.CachePut;
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
        return operationAnnotation instanceof CachePut;
    }

    @Override
    protected void executeCacheOperate(Annotation operationAnnotation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        CachePut cachePut = (CachePut) operationAnnotation;

        if (this.canDoCacheOperate(cachePut.condition(), cachePut.unless(), expressionMetadata)) {
            //调用方法
            methodReturnValueFunction.proceed();
            return;
        }
        //默认为方法的返回值，当设置了缓存值就用指定的缓存值
        //得到过期时间
        long expireTime = CacheUtils.getExpireTime(cachePut.expireTime(), cachePut.randomTime());

        Object key = parseCacheKey(expressionMetadata, cachePut.key());
        PutParam putParam = new PutParam();
        putParam.setWrapperCacheName(super.getWrapperCacheName(cachePut.cacheName(), expressionMetadata));
        putParam.setOriginCacheName(cachePut.cacheName());
        putParam.setKey(JsonUtils.toJsonString(key));

        putParam.setExpireTime(expireTime);
        putParam.setTimeUnit(cachePut.timeUnit());
        CacheOperate cacheOperate = super.cacheOperateSupport.getCacheOperate(cachePut.cacheName());
        if (!CollectionUtils.isEmpty(cacheOperateInvocationHooks)) {
            for (CacheOperateInvocationHook cacheOperateInvocationHook : cacheOperateInvocationHooks) {
                if (!cacheOperateInvocationHook.before(putParam, cacheOperate)) {
                    //终止操作也调用方法
                    methodReturnValueFunction.proceed();
                    return;
                }
            }
        }
        //调用方法
        methodReturnValueFunction.proceed();
        Object cacheValue = expressionParser.parserExpression(expressionMetadata, cachePut.cacheValue());
        putParam.setCacheValue(cacheValue);

        cacheOperate.put(putParam);
        if (!CollectionUtils.isEmpty(cacheOperateInvocationHooks)) {
            for (CacheOperateInvocationHook cacheOperateInvocationHook : cacheOperateInvocationHooks) {
                cacheOperateInvocationHook.after(putParam, cacheOperate);
            }
        }


    }

}
