package com.lz.core.cache.common;

import java.lang.reflect.Method;

/**
 * @author luyi
 */
public interface CacheProcess {


    /**
     * 得到缓存的key
     *
     * @param method 拦截的方法
     * @param args   请求参数
     * @return key
     */

    String getCacheKey(Method method, Object... args);

    /**
     * 处理缓存
     *
     * @param target 处理的对象
     * @param method 拦截的方法
     * @param args   请求参数
     * @return 处理后的返回值
     */
    Object process(Object target, Method method, Object[] args);

}
