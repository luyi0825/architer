package com.lz.core.cache.common.key;


import com.lz.core.cache.common.CacheOperation;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author luyi
 * 默认缓存key 生成器
 */
public class DefaultKeyGenerator implements KeyGenerator {

    private final static String CACHE_PREFIX = "cachePrefix";

    private static final int MAX_KEY_SUFFIX_SIZE = 16;


    @Override
    public String getKey(Object target, Method method, Object[] args, CacheOperation cacheOperation) {
        String cachePrefix = getKeyPrefix(target, method, cacheOperation);
        if (args == null) {
            return cachePrefix;
        }
        String cacheSuffix = this.getCacheSuffix(method, args);
        if (!StringUtils.isEmpty(cacheSuffix)) {
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
    private String getCacheSuffix(Method method, Object[] args) {
        StringJoiner joiner = new StringJoiner("_");
        Annotation[][] annotations = method.getParameterAnnotations();
        int i = 0;
        for (Annotation[] annotation : annotations) {
            if (annotation.length > 0) {
                joiner.add(Objects.toString(args[i], "#"));
            }
            i++;
        }
        int length = joiner.length();
        if (length == 0) {
            for (Object arg : args) {
                joiner.add(Objects.toString(arg, "#"));
            }
        }
        //太长了就采用加密的方式缩短key
        if (joiner.length() > MAX_KEY_SUFFIX_SIZE) {
            DigestUtils.md5Digest(joiner.toString().getBytes(StandardCharsets.UTF_8));
        }
        return joiner.toString();
    }


    /**
     * 描述：得到缓存前缀
     *
     * @TODO 将前缀缓存起来，避免频繁的反射影响效率
     * @author luyi
     * @date 2021/4/15
     */
    private String getKeyPrefix(Object target, Method method, CacheOperation cacheOperation) {
        String cachePrefix = cacheOperation.getCachePrefix();
        if (StringUtils.isEmpty(cachePrefix)) {
            cachePrefix = target.getClass().getName() + "." + method.getName();
        }
        return cachePrefix;
    }
}
