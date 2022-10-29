package io.github.architers.context.cache.operation;

import io.github.architers.context.Symbol;
import io.github.architers.context.cache.annotation.BatchDeleteCache;
import io.github.architers.context.cache.BatchValueUtils;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * 批量删除操作处理
 *
 * @author luyi
 */
public class BatchDeleteOperationHandler extends CacheOperationHandler {


    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof BatchDeleteCache;
    }

    @Override
    protected void execute(Annotation operationAnnotation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {
        BatchDeleteCache batchDeleteCache = (BatchDeleteCache) operationAnnotation;
        //执行方法
        methodReturnValueFunction.proceed();
        //判断是否能够执行
        if (!super.canDoCacheOperate(batchDeleteCache.condition(), batchDeleteCache.unless(), expressionMetadata)) {
            return;
        }
        //解析key
        Object keyValues = expressionParser.parserExpression(expressionMetadata, batchDeleteCache.keys());
        Collection<?> keys = BatchValueUtils.parseKeys(keyValues, Symbol.COLON);
        //得到缓存名称
        KeyGenerator keyGenerator = super.keyGeneratorFactory.getKeyGenerator(batchDeleteCache.keyGenerator());
        String cacheName = keyGenerator.generator(expressionMetadata, batchDeleteCache.cacheName());
        BatchDeleteParam batchDeleteParam = new BatchDeleteParam();
        batchDeleteParam.setCacheName(cacheName);
        batchDeleteParam.setAsync(batchDeleteCache.async());
        batchDeleteParam.setKeys(keys);
        //批量删除
        CacheOperate cacheOperate = super.cacheOperateFactory.getCacheOperate(batchDeleteCache.cacheOperate());
        cacheOperate.batchDelete(batchDeleteParam);
    }
}
