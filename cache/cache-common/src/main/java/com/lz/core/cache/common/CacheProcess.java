package com.lz.core.cache.common;

import com.lz.core.cache.common.utils.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;

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
     * @param method 拦截的方法
     * @param args   请求参数
     * @return 处理后的返回值
     */
    Object process(Object target, Method method, Object[] args);

}
