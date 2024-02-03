package io.github.architers.context.cache.operate;

import io.github.architers.common.expression.method.ExpressionMetadata;
import io.github.architers.context.cache.annotation.CacheEvictAll;
import io.github.architers.context.cache.model.EvictAllParam;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;

import java.lang.annotation.Annotation;

/**
 * 驱逐所有的缓存
 *
 * @author luyi
 * @since 1.0.0
 */
public class CacheEvictAllOperationHandler extends CacheChangeOperationHandler {
    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof CacheEvictAll;
    }

    @Override
    protected void executeCacheOperate(Annotation operationAnnotation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        CacheEvictAll cacheEvictAll = (CacheEvictAll) operationAnnotation;

        //判断是否能够执行
        if (super.canDoCacheOperate(cacheEvictAll.condition(), cacheEvictAll.unless(), expressionMetadata)) {
            //返回
            return;
        }
        String wrapperCacheName = this.getWrapperCacheName(cacheEvictAll.cacheName(), expressionMetadata);
        CacheOperate cacheOperate = super.cacheOperateManager.getCacheOperate(cacheEvictAll.cacheName());
        EvictAllParam evictAllParam = new EvictAllParam();
        evictAllParam.setAsync(cacheEvictAll.async());
        evictAllParam.setOriginCacheName(cacheEvictAll.cacheName());
        evictAllParam.setWrapperCacheName(wrapperCacheName);

        super.beforeInvocation(evictAllParam, cacheOperate);
        cacheOperate.deleteAll(evictAllParam);
        super.afterInvocation(evictAllParam, cacheOperate);

    }
}
