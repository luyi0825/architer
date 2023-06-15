package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.CacheConstants;
import io.github.architers.context.cache.annotation.CacheBatchEvict;
import io.github.architers.context.cache.operate.hook.CacheOperateInvocationHook;
import io.github.architers.context.cache.utils.BatchValueUtils;
import io.github.architers.context.cache.model.BatchEvictParam;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * 批量删除操作处理
 *
 * @author luyi
 */
public class CacheBatchEvictOperationHandler extends BaseCacheOperationHandler {


    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof CacheBatchEvict;
    }


    @Override
    protected void executeCacheOperate(Annotation operationAnnotation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        CacheBatchEvict cacheBatchEvict = (CacheBatchEvict) operationAnnotation;
        //判断是否能够执行
        if (super.canDoCacheOperate(cacheBatchEvict.condition(), cacheBatchEvict.unless(), expressionMetadata)) {
            return;
        }
        //解析key
        Object keys = expressionParser.parserExpression(expressionMetadata, cacheBatchEvict.keys());

        if (cacheBatchEvict.parseKeys()) {
            keys = BatchValueUtils.parseBatchEvictKeys(keys, CacheConstants.CACHE_SPLIT);
        }
        //得到缓存名称
        String wrapperCacheName = super.getWrapperCacheName(cacheBatchEvict.cacheName(), expressionMetadata);
        BatchEvictParam batchEvictParam = new BatchEvictParam();
        batchEvictParam.setWrapperCacheName(wrapperCacheName);
        batchEvictParam.setOriginCacheName(cacheBatchEvict.cacheName());
        batchEvictParam.setAsync(cacheBatchEvict.async());
        batchEvictParam.setKeys((Collection<?>) keys);

        CacheOperate cacheOperate = super.cacheOperateSupport.getCacheOperate(cacheBatchEvict.cacheName());

        if (!CollectionUtils.isEmpty(cacheOperateInvocationHooks)) {
            for (CacheOperateInvocationHook cacheOperateInvocationHook : cacheOperateInvocationHooks) {
                cacheOperateInvocationHook.before(batchEvictParam, cacheOperate);
            }
        }
        cacheOperate.batchDelete(batchEvictParam);
        if (cacheBatchEvict.beforeInvocation()) {
            super.beforeInvocation(batchEvictParam, cacheOperate);
        } else {
            super.afterInvocation(batchEvictParam, cacheOperate);
        }
    }
}
