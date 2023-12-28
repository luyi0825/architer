package io.github.architers.context.cache.operate;


import io.github.architers.context.cache.annotation.CacheEvict;
import io.github.architers.context.cache.model.EvictParam;
import io.github.architers.context.cache.proxy.MethodReturnValueFunction;
import io.github.architers.context.expression.ExpressionMetadata;
import io.github.architers.context.utils.JsonUtils;

import java.lang.annotation.Annotation;


/**
 * 缓存驱逐操作
 * 默认：先删,后操作:防止redis出错了，造成数据不一致问题
 * <li>
 * 比如更新的时候:我们删除数据，如果delete操作在后，redis突然故障导致删除数据失败，导致我们从缓存取的值就有问题。
 * 当然如果redis故障异常，数据库开启了事物的情况下不会出现这个问题
 *
 * @author luyi
 */
public class CacheEvictOperationHandler extends BaseCacheOperationHandler {


    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof CacheEvict;
    }

    @Override
    protected void executeCacheOperate(Annotation operation, ExpressionMetadata expressionMetadata, MethodReturnValueFunction methodReturnValueFunction) throws Throwable {

        CacheEvict cacheEvict = (CacheEvict) operation;
        if (canDoCacheOperate(cacheEvict.condition(), cacheEvict.unless(), expressionMetadata)) {
            return;
        }
        EvictParam evictParam = new EvictParam();
        evictParam.setWrapperCacheName(getWrapperCacheName(cacheEvict.cacheName(), expressionMetadata));
        evictParam.setOriginCacheName(cacheEvict.cacheName());
        Object key = super.parseCacheKey(expressionMetadata, cacheEvict.key());
        CacheOperate cacheOperate = cacheOperateSupport.getCacheOperate(cacheEvict.cacheName());
        evictParam.setKey(JsonUtils.toJsonString(key));
        evictParam.setAsync(cacheEvict.async());
        //删除缓存后，再调用方法
        cacheOperate.delete(evictParam);

        if (cacheEvict.beforeInvocation()) {
            super.beforeInvocation(evictParam, cacheOperate);
        } else {
            super.afterInvocation(evictParam, cacheOperate);
        }
    }

}
