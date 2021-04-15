package com.lz.core.cache.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author luyi
 */
public interface CacheProcess {

    /**
     * 处理缓存
     *
     * @param annotation 缓存注解的class
     * @param target     处理的对象
     * @param method     拦截的方法
     * @param args       请求参数
     * @return 处理后的返回值
     */
    Object process(Object target, Method method, Object[] args, Class<?> annotation) throws NoSuchFieldException, IllegalAccessException;

}
