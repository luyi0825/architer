package io.github.architers.context.cache.operate;

import io.github.architers.context.Symbol;
import io.github.architers.context.cache.annotation.CacheBatchEvict;
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
public class BatchEvictOperationHandler extends BaseCacheOperationHandler {


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
        Object keyValues = expressionParser.parserExpression(expressionMetadata, cacheBatchEvict.keys());
        Collection<?> keys = BatchValueUtils.parseKeys(keyValues, Symbol.COLON);
        //得到缓存名称
        String wrapperCacheName = super.getWrapperCacheName(cacheBatchEvict.cacheName(), expressionMetadata);
        BatchEvictParam batchEvictParam = new BatchEvictParam();
        batchEvictParam.setWrapperCacheName(wrapperCacheName);
        batchEvictParam.setOriginCacheName(cacheBatchEvict.cacheName());
        batchEvictParam.setAsync(cacheBatchEvict.async());
        batchEvictParam.setKeys(keys);

        CacheOperate cacheOperate = super.cacheOperateSupport.getCacheOperate(cacheBatchEvict.cacheName());

        if (!CollectionUtils.isEmpty(cacheOperateInvocationHooks)) {
            for (CacheOperateInvocationHook cacheOperateInvocationHook : cacheOperateInvocationHooks) {
                cacheOperateInvocationHook.before(batchEvictParam, cacheOperate);
            }
        }
        //批量删除,先删除缓存，再操作数据库
        cacheOperate.batchDelete(batchEvictParam);
        if (!CollectionUtils.isEmpty(cacheOperateInvocationHooks)) {
            for (CacheOperateInvocationHook cacheOperateInvocationHook : cacheOperateInvocationHooks) {
                cacheOperateInvocationHook.after(batchEvictParam, cacheOperate);
            }
        }
    }
}
