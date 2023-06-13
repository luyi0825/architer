package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.annotation.CacheEvictAll;
import io.github.architers.context.cache.model.DeleteAllParam;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;

import java.lang.annotation.Annotation;

/**
 * 驱逐所有的缓存
 *
 * @author luyi
 * @since 1.0.0
 */
public class CacheEvictAllOperationHandler extends BaseCacheOperationHandler {
    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof CacheEvictAll;
    }

    @Override
    protected void executeCacheOperate(Annotation operationAnnotation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        CacheEvictAll cacheEvictAll = (CacheEvictAll) operationAnnotation;
        String wrapperCacheName = this.getWrapperCacheName(cacheEvictAll.cacheName(), expressionMetadata);
        CacheOperate cacheOperate = super.cacheOperateSupport.getCacheOperate(cacheEvictAll.cacheName());
        DeleteAllParam deleteAllParam = new DeleteAllParam();
        deleteAllParam.setAsync(cacheEvictAll.async());
        deleteAllParam.setOriginCacheName(cacheEvictAll.cacheName());
        deleteAllParam.setWrapperCacheName(wrapperCacheName);
        cacheOperate.deleteAll(deleteAllParam);
        if (cacheEvictAll.beforeInvocation()) {
            super.beforeInvocation(deleteAllParam, cacheOperate);
        } else {
            super.afterInvocation(deleteAllParam, cacheOperate);
        }
    }
}
