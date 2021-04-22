package com.lz.core.cache.common.key;


import com.lz.core.cache.common.operation.CacheOperation;
import com.lz.core.cache.common.operation.CacheOperationMetadata;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author luyi
 * 默认缓存key 生成器
 */
public class DefaultKeyGenerator implements KeyGenerator {

    private ElExpressionKeyParser elExpressionKeyParser = new ElExpressionKeyParser();

    /**
     * 前后缀的分割符号
     */
    private String separator = "::";

    private static final int MAX_KEY_SUFFIX_SIZE = 16;


    @Override
    public String getKey(CacheOperationMetadata metadata) {
        CacheOperation cacheOperation = metadata.getCacheOperation();
        if (!StringUtils.isEmpty(cacheOperation.getCacheName())) {
            return cacheOperation.getCacheName();
        }
        String cachePrefix = getKeyPrefix(metadata.getTarget(), metadata.getTargetMethod(), cacheOperation);
        if (metadata.getArgs() == null) {
            return cachePrefix;
        }
        String cacheSuffix = "";
        if (StringUtils.isEmpty(cacheOperation.getSuffix())) {
            cacheSuffix = this.getCacheSuffix(metadata.getTargetMethod(), metadata.getArgs(), cacheOperation);
        } else {
            cacheSuffix = elExpressionKeyParser.generateKey(metadata, cacheOperation.getSuffix());
        }
        if (!StringUtils.isEmpty(cacheSuffix)) {
            return cachePrefix + getSeparator() + cacheSuffix;
        }
        return cachePrefix;
    }


    /**
     * 描述：得到缓存后缀
     *
     * @author luyi
     * @date 2021/4/15
     */
    private String getCacheSuffix(Method method, Object[] args, CacheOperation cacheOperation) {
        StringJoiner joiner = new StringJoiner("_");
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
        String cachePrefix = cacheOperation.getPrefix();
        if (StringUtils.isEmpty(cachePrefix)) {
            cachePrefix = target.getClass().getName();
        }
        return cachePrefix;
    }


    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
