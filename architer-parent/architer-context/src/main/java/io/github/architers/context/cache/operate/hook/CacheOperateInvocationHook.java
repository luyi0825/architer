package io.github.architers.context.cache.operate.hook;

import io.github.architers.context.cache.model.BaseCacheParam;
import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.CacheOperateContext;

/**
 * 缓存操作调用钩子函数
 *
 * @author luyi
 */
public interface CacheOperateInvocationHook {


    /**
     * 调用方法之前的操作
     *
     * @param cacheOperationParam 缓存值为空
     */
    boolean before(BaseCacheParam cacheOperationParam, CacheOperateContext cacheOperateContext);

    /**
     * 调用方法之后的缓存操作
     */
    void after(BaseCacheParam cacheOperationParam,CacheOperateContext cacheOperateContext);
}
