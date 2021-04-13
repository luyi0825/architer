package com.lz.core.cache.common.key;

import java.lang.reflect.Method;

/**
 * @author luyi
 * @see com.lz.core.cache.common.enums.KeyStrategy#ANNOTATIONS
 * 注解策略
 */
public class AnnotationsKeyGenerator implements KeyGenerator {
    @Override
    public String getKey(Method method, Object[] args) {
        return null;
    }
}
