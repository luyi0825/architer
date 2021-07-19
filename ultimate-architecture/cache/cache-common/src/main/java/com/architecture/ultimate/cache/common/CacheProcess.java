package com.core.cache.common;

import com.core.cache.common.operation.CacheOperation;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author luyi
 */
public interface CacheProcess {

    /**
     * 处理缓存
     *
     * @param cacheOperations 缓存operation
     * @param target          处理的对象
     * @param method          拦截的方法
     * @param args            请求参数
     * @return 处理后的返回值
     */
    Object process(Object target, Method method, Object[] args, Collection<CacheOperation> cacheOperations);

}
