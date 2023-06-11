package io.github.architers.context.cache.operate;


import io.github.architers.context.cache.annotation.BatchPutCache;
import io.github.architers.context.cache.model.BatchPutParam;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;

/**
 * 批量放置缓存操作处理
 *
 * @author luyi
 */
public class BatchPutOperationHandler extends BaseCacheOperationHandler {


    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof BatchPutCache;
    }

    @Override
    protected void executeCacheOperate(Annotation operationAnnotation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        BatchPutCache batchPutCache = (BatchPutCache) operationAnnotation;
        //执行方法
        methodReturnValueFunction.proceed();
        //判断是否能够执行
        if (!super.canDoCacheOperate(batchPutCache.condition(), batchPutCache.unless(), expressionMetadata)) {
            return;
        }
        String wrapperCacheName = super.getWrapperCacheName(batchPutCache.cacheName(),expressionMetadata);
        BatchPutParam batchPutParam = new BatchPutParam();
        batchPutParam.setOriginCacheName(batchPutCache.cacheName());
        batchPutParam.setWrapperCacheName(wrapperCacheName);
        batchPutParam.setAsync(batchPutCache.async());
        batchPutParam.setTimeUnit(batchPutCache.timeUnit());
        batchPutParam.setExpireTime(batchPutCache.expireTime());
        batchPutParam.setExpireTime(batchPutCache.randomTime());
        Object batchCacheValue = super.expressionParser.parserExpression(expressionMetadata, batchPutCache.cacheValue());
        batchPutParam.setBatchCacheValue(batchCacheValue);
        //批量删除
        CacheOperate cacheOperate = super.cacheOperateSupport.getCacheOperate(batchPutCache.cacheName());
        cacheOperate.batchPut(batchPutParam);
        if (!CollectionUtils.isEmpty(cacheOperateEndHooks)) {
            for (CacheOperateEndHook cacheOperateEndHook : cacheOperateEndHooks) {
                cacheOperateEndHook.end(batchPutParam, cacheOperate);
            }
        }

    }
}
