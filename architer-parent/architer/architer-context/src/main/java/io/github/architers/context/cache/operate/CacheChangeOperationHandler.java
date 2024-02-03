package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.model.BaseCacheParam;
import io.github.architers.context.cache.operate.hook.CacheChangeOperateInvocationHook;
import io.github.architers.context.cache.operate.hook.CacheOperateInvocationHook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

public abstract class CacheChangeOperationHandler extends BaseCacheOperationHandler {

    @Autowired(required = false)
    protected List<CacheChangeOperateInvocationHook> cacheChangeOperateInvocationHooks;

    protected boolean beforeInvocation(BaseCacheParam cacheParam, CacheOperate cacheOperate) {
        if (!CollectionUtils.isEmpty(cacheChangeOperateInvocationHooks)) {
            //调用方法之前的钩子函数
            for (CacheOperateInvocationHook cacheOperateInvocationHook : cacheChangeOperateInvocationHooks) {
                if (!cacheOperateInvocationHook.before(cacheParam, cacheOperate)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void afterInvocation(BaseCacheParam cacheParam, CacheOperate cacheOperate) {
        if (!CollectionUtils.isEmpty(cacheChangeOperateInvocationHooks)) {
            //调用方法之前的钩子函数
            for (CacheOperateInvocationHook cacheOperateInvocationHook : cacheChangeOperateInvocationHooks) {
                cacheOperateInvocationHook.after(cacheParam, cacheOperate);
            }
        }
    }
}
