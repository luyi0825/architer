package com.architecture.context.cache.operation;


import com.architecture.context.cache.annotation.DeleteCache;
import com.architecture.context.cache.proxy.MethodInvocationFunction;
import com.architecture.context.expression.ExpressionMetadata;


import java.util.List;


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


    @Override
    public boolean match(CacheOperation operation) {
        return operation instanceof DeleteCache;
    }

    @Override
    protected void execute(CacheOperation operation, ExpressionMetadata expressionMetadata, MethodInvocationFunction methodInvocationFunction) throws Throwable {
        List<String> cacheKeys = this.getCacheKeys(operation, expressionMetadata);
        cacheService.multiDelete(cacheKeys);
        methodInvocationFunction.proceed();
    }
}
