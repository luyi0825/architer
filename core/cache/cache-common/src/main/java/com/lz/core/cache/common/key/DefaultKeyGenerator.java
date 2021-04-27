package com.lz.core.cache.common.key;


import com.lz.core.cache.common.exception.CacheAnnotationIllegalException;
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

    private final ElExpressionKeyParser elExpressionKeyParser = new ElExpressionKeyParser();

    /**
     * 前后缀的分割符号
     */
    private String separator = "::";


    @Override
    public String getKey(CacheOperationMetadata metadata) {
        CacheOperation cacheOperation = metadata.getCacheOperation();
        if (!StringUtils.isEmpty(cacheOperation.getCacheName())) {
            return elExpressionKeyParser.generateKey(metadata, cacheOperation.getCacheName());
        }
        //如果cacheName不存在，前缀后缀必须都配置
        if (StringUtils.isEmpty(cacheOperation.getPrefix()) || StringUtils.isEmpty(cacheOperation.getSuffix())) {
            throw new CacheAnnotationIllegalException("prefix and suffix must exist when cacheName is null");
        }
        String cachePrefix = elExpressionKeyParser.generateKey(metadata, cacheOperation.getPrefix());
        String cacheSuffix = elExpressionKeyParser.generateKey(metadata, cacheOperation.getSuffix());
        return cachePrefix + getSeparator() + cacheSuffix;
    }


    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
