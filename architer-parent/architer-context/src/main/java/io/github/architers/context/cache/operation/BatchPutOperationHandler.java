package io.github.architers.context.cache.operation;


import io.github.architers.context.cache.annotation.BatchPutCache;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 批量删除操作处理
 *
 * @author luyi
 */
public class BatchPutOperationHandler extends CacheOperationHandler {


    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof BatchPutCache;
    }

    @Override
    protected void execute(Annotation operationAnnotation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        BatchPutCache batchPutCache = (BatchPutCache) operationAnnotation;
        //执行方法
        methodReturnValueFunction.proceed();
        //判断是否能够执行
        if (!super.canDoCacheOperate(batchPutCache.condition(), batchPutCache.unless(), expressionMetadata)) {
            return;
        }
        //得到缓存名称
        KeyGenerator keyGenerator = super.keyGeneratorFactory.getKeyGenerator(batchPutCache.keyGenerator());
        String cacheName = keyGenerator.generator(expressionMetadata, batchPutCache.cacheName());
        BatchPutParam batchPutParam = new BatchPutParam();
        batchPutParam.setCacheName(cacheName);
        batchPutParam.setAsync(batchPutCache.async());
        batchPutParam.setTimeUnit(batchPutCache.timeUnit());
        batchPutParam.setExpireTime(batchPutCache.expireTime());
        batchPutParam.setExpireTime(batchPutCache.randomTime());
        Object batchCacheValue = super.expressionParser.parserExpression(expressionMetadata,
                batchPutCache.cacheValue());
        batchPutParam.setBatchCacheValue(batchCacheValue);
        //批量删除
        CacheOperate cacheOperate = super.cacheOperateFactory.getCacheOperate(batchPutCache.cacheOperate());
        cacheOperate.batchPut(batchPutParam);
    }
}
