package com.core.cache.common.key;


import com.core.cache.common.exception.CacheAnnotationIllegalException;
import com.core.cache.common.operation.CacheOperation;
import com.core.cache.common.operation.CacheOperationMetadata;
import com.core.cache.common.CacheExpressionParser;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author luyi
 * 默认缓存key 生成器
 * @TODO 多个cacheName的处理
 */
public class DefaultKeyGenerator implements KeyGenerator {

    private final CacheExpressionParser cacheExpressionParser;

    /**
     * 前后缀的分割符号
     */
    private String separator = "::";

    public DefaultKeyGenerator(CacheExpressionParser cacheExpressionParser) {
        this.cacheExpressionParser = cacheExpressionParser;
    }

    @Override
    public String getKey(CacheOperationMetadata metadata) {
        CacheOperation cacheOperation = metadata.getCacheOperation();
        String[] cacheName = cacheOperation.getCacheName();
        String cacheKey = cacheOperation.getKey();
        if (StringUtils.isEmpty(cacheKey)) {
            throw new CacheAnnotationIllegalException("cacheKey is null");
        }
        String cacheSuffix = Objects.requireNonNull(cacheExpressionParser.executeParse(metadata, cacheKey)).toString();
        if (ArrayUtils.isEmpty(cacheName)) {
            return cacheSuffix;
        } else {
            return cacheName[0] + getSeparator() + cacheSuffix;
        }
    }


    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
