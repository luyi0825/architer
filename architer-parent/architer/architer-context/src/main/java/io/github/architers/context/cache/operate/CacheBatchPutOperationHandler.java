package io.github.architers.context.cache.operate;


import io.github.architers.common.expression.method.ExpressionMetadata;
import io.github.architers.context.cache.annotation.CacheBatchPut;
import io.github.architers.context.cache.model.BatchPutParam;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;

import java.lang.annotation.Annotation;

/**
 * 批量放置缓存操作处理
 *
 * @author luyi
 */
public class CacheBatchPutOperationHandler extends BaseCacheOperationHandler {


    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof CacheBatchPut;
    }

    @Override
    protected void executeCacheOperate(Annotation operationAnnotation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        CacheBatchPut cacheBatchPut = (CacheBatchPut) operationAnnotation;
        //判断是否能够执行
        if (super.canDoCacheOperate(cacheBatchPut.condition(), cacheBatchPut.unless(), expressionMetadata)) {
            //执行方法
            return;
        }
        String wrapperCacheName = super.getWrapperCacheName(cacheBatchPut.cacheName(), expressionMetadata);
        BatchPutParam batchPutParam = new BatchPutParam();
        batchPutParam.setOriginCacheName(cacheBatchPut.cacheName());
        batchPutParam.setWrapperCacheName(wrapperCacheName);
        batchPutParam.setAsync(cacheBatchPut.async());
        batchPutParam.setTimeUnit(cacheBatchPut.timeUnit());
        batchPutParam.setExpireTime(cacheBatchPut.expireTime());
        batchPutParam.setExpireTime(cacheBatchPut.randomTime());
        Object batchCacheValue = super.expressionParser.parserExpression(expressionMetadata, cacheBatchPut.cacheValue());
        batchPutParam.setBatchCacheValue(batchCacheValue);
        //批量删除
        CacheOperate cacheOperate = super.cacheOperateManager.getCacheOperate(cacheBatchPut.cacheName());
        cacheOperate.batchPut(batchPutParam);

        afterInvocation(batchPutParam, cacheOperate);

    }
}
