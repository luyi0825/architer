package io.github.architers.context.cache.operation;


import io.github.architers.context.cache.annotation.DeleteCache;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;

import java.lang.annotation.Annotation;


/**
 * 对应DeleteCacheOperation
 * 默认：先删,后操作:防止redis出错了，造成数据不一致问题
 * <li>
 * 比如更新的时候:我们删除数据，如果delete操作在后，redis突然故障导致删除数据失败，导致我们从缓存取的值就有问题。
 * 当然如果redis故障异常，数据库开启了事物的情况下不会出现这个问题
 *
 * @author luyi
 */
public class DeleteCacheOperationHandler extends CacheOperationHandler {

    private static final int END_ORDER = 3;

    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof DeleteCache;
    }

    @Override
    protected void execute(Annotation operation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {

        //调用方法（没有cacheable就是真的调用）
        methodReturnValueFunction.proceed();
        DeleteCache deleteCache = (DeleteCache) operation;
        if (!canDoCacheOperate(deleteCache.condition(), deleteCache.unless(), expressionMetadata)) {
            return;
        }
        CacheOperate cacheOperate = cacheOperateFactory.getCacheOperate(deleteCache.cacheOperate());
        DeleteParam deleteParam = new DeleteParam();
        KeyGenerator keyGenerator = keyGeneratorFactory.getKeyGenerator(deleteCache.keyGenerator());
        String cachePrefixes = keyGenerator.generator(expressionMetadata, deleteCache.cacheName());

        deleteParam.setCacheName(cachePrefixes);
        Object key = super.parseCacheKey(expressionMetadata, deleteCache.key());

      //  deleteCacheParam.setCacheKey(key);
        deleteParam.setAsync(deleteCache.async());
       // deleteCacheParam.setKeyGenerator();
       // deleteCacheParam.setCacheOperate(super.cacheCacheOperateFactory.getCacheOperate(deleteCache.cacheOperate()));
        //删除缓存
        cacheOperate.delete(deleteParam);
    }


}
