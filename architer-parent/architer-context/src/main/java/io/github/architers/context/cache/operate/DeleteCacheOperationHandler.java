package io.github.architers.context.cache.operate;


import io.github.architers.context.cache.annotation.DeleteCache;
import io.github.architers.context.cache.model.DeleteParam;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;
import io.github.architers.context.utils.JsonUtils;
import org.springframework.util.CollectionUtils;

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
public class DeleteCacheOperationHandler extends BaseCacheOperationHandler {

    private static final int END_ORDER = 3;

    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof DeleteCache;
    }

    @Override
    protected void executeCacheOperate(Annotation operation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {


        DeleteCache deleteCache = (DeleteCache) operation;
        if (!canDoCacheOperate(deleteCache.condition(), deleteCache.unless(), expressionMetadata)) {
            methodReturnValueFunction.proceed();
            return;
        }

        CacheOperate cacheOperate = cacheOperateSupport.getCacheOperate(deleteCache.cacheName());
        DeleteParam deleteParam = new DeleteParam();
        deleteParam.setWrapperCacheName(getWrapperCacheName(deleteCache.cacheName(), expressionMetadata));
        deleteParam.setOriginCacheName(deleteCache.cacheName());
        Object key = super.parseCacheKey(expressionMetadata, deleteCache.key());

        deleteParam.setKey(JsonUtils.toJsonString(key));
        deleteParam.setAsync(deleteCache.async());
        if (!CollectionUtils.isEmpty(cacheOperateHooks)) {
            for (CacheOperateHook cacheOperateHook : cacheOperateHooks) {
                cacheOperateHook.before(deleteParam, cacheOperate);
            }
        }
        //删除缓存后，再调用方法
        cacheOperate.delete(deleteParam);
        methodReturnValueFunction.proceed();
        if (!CollectionUtils.isEmpty(cacheOperateHooks)) {
            for (CacheOperateHook cacheOperateHook : cacheOperateHooks) {
                cacheOperateHook.end(deleteParam, cacheOperate);
            }
        }
    }


}
