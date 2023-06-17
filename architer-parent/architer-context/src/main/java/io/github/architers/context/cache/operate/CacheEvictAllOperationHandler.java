package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.annotation.CacheEvictAll;
import io.github.architers.context.cache.model.EvictAllParam;
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

        CacheOperateContext cacheOperateContext = super.cacheOperateSupport.getCacheOperateContext(cacheEvictAll.cacheName());
        String wrapperCacheName = this.getWrapperCacheName(cacheOperateContext, expressionMetadata,cacheEvictAll.cacheName());

        EvictAllParam evictAllParam = new EvictAllParam();
        evictAllParam.setAsync(cacheEvictAll.async());
        evictAllParam.setOriginCacheName(cacheEvictAll.cacheName());
        evictAllParam.setWrapperCacheName(wrapperCacheName);
        cacheOperateContext.getCacheOperate().deleteAll(evictAllParam);
        if (cacheEvictAll.beforeInvocation()) {
            super.beforeInvocation(evictAllParam, cacheOperateContext);
        } else {
            super.afterInvocation(evictAllParam, cacheOperateContext);
        }
    }
}
