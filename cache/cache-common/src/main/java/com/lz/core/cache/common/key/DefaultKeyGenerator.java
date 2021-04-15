package com.lz.core.cache.common.key;


import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import java.util.Objects;

/**
 * @author luyi
 * 缓存key 生成器
 */
public class DefaultKeyGenerator implements KeyGenerator {

    private final static String CACHE_PREFIX = "cachePrefix";

    private static final int MAX_KEY_SUFFIX_SIZE = 16;


    @Override
    public String getKey(Object target, Method method, Object[] args, Class<Annotation> annotation) {
        String cachePrefix = getKeyPrefix(target, method, annotation);
        if (args == null) {
            return cachePrefix;
        }
        String cacheSuffix = this.getCacheSuffix(args);
        if (StringUtils.isEmpty(cacheSuffix)) {
            return cachePrefix + "::" + cacheSuffix;
        }
        return cachePrefix;
    }

    /**
     * 描述：得到缓存后缀
     *
     * @author luyi
     * @date 2021/4/15
     */
    private String getCacheSuffix(Object[] args) {
        StringBuilder builder = new StringBuilder();
        Method method = null;
        Annotation[][] annotations = method.getParameterAnnotations();
        int i = 0;
        for (Annotation[] annotation : annotations) {
            if (annotation.length > 0) {
                builder.append(Objects.toString(args[i], "#"));
                builder.append("_");
            }
        }
        //太长了就采用加密的方式缩短key
        if (builder.length() > MAX_KEY_SUFFIX_SIZE) {
            DigestUtils.md5Digest(builder.toString().getBytes(StandardCharsets.UTF_8));
        }
        return builder.toString();
    }


    /**
     * 描述：得到缓存前缀
     *
     * @TODO 将前缀缓存起来，避免频繁的反射影响效率
     * @author luyi
     * @date 2021/4/15
     */
    private String getKeyPrefix(Object target, Method method, Class<Annotation> annotation) {
        String cachePrefix = null;
        Annotation cacheAnnotation = method.getAnnotation(annotation);
        try {
            Field field = cacheAnnotation.getClass().getField(CACHE_PREFIX);
            cachePrefix = (String) field.get(target);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            //TODO
        }
        if (StringUtils.isEmpty(cachePrefix)) {
            cachePrefix = target.getClass().getName() + "." + method.getName();
        }
        return cachePrefix;
    }
}
