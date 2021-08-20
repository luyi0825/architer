package com.architecture.context.cache.operation;


import com.architecture.context.cache.annotation.DeleteCache;
import com.architecture.context.cache.operation.CacheOperation;


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
    @Override
    public boolean match(Annotation operationAnnotation) {
        return operationAnnotation instanceof DeleteCache;
    }

    @Override
    protected Object executeCacheHandler(String[] keys, CacheOperationMetadata metadata) {
        CacheOperation cacheOperation = metadata.getCacheOperation();
        for (String key : keys) {
            writeCache(cacheOperation.isAsync(), () -> cacheManager.deleteCache(key));
        }
        return invoke(metadata);
    }


}
