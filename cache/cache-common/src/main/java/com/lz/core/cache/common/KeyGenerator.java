package com.lz.core.cache.common;

import java.lang.reflect.Method;

/**
 * @author luyi
 * 缓存key 生成器
 */
public interface KeyGenerator {

    String getKey(Method method, Object[] args);

}
