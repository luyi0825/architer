package com.lz.core.cache.common;


import com.lz.core.cache.common.utils.JsonUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 业务层AOP处理器
 *
 * @author luyi
 * @date 2020/12/17
 */
public abstract class CacheProcess {


    /**
     * 描述:是否支持
     *
     * @param method 拦截缓存对应的方法
     * @author luyi
     * @date 2020/12/26 上午1:18
     */
    public abstract boolean supportBefore(Method method);

    /**
     * 描述:得到缓存的key
     *
     * @author luyi
     * @date 2020/12/20 下午3:11
     */
    public String getCacheKey(ProceedingJoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Class<?> cacheClass = jp.getTarget().getClass();
        Method method = methodSignature.getMethod();
        Object[] args = jp.getArgs();
        String paramKey = null;
        if (args.length > 0) {
            //说明有参数,md5作为作为key(防止参数为javaBean,造成key过长)
            paramKey = JsonUtils.toJsonString(args);
        }
        String className = cacheClass.getTypeName();
        String methodName = method.getName();
        if (!StringUtils.isEmpty(paramKey)) {
            return String.join("_", className, methodName, paramKey);
        }
        return String.join("_", className, methodName);
    }

    /**
     * 描述:处理缓存值
     *
     * @param jp 切点信息
     * @throws Throwable 缓存处理抛出的异常
     * @author luyi
     * @date 2020/12/26 上午1:15
     */
    public abstract Object processCache(ProceedingJoinPoint jp) throws Throwable;
}
