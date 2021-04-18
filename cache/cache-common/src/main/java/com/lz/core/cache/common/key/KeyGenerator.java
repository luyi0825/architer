package com.lz.core.cache.common.key;

import com.lz.core.cache.common.CacheOperation;
import com.lz.core.cache.common.enums.KeyStrategy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author luyi
 * 缓存key 生成器
 */
public interface KeyGenerator {


    /**
     * 得到key
     *
     * @param annotation 对应的注解
     * @param target     对象
     * @param method     方法
     * @param args       参数
     * @return 缓存的key
     */
    String getKey(Object target, Method method, Object[] args, CacheOperation annotation);


}
