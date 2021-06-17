package com.core.cache.common.key;


import com.core.cache.common.exception.CacheAnnotationIllegalException;
import com.core.cache.common.operation.CacheOperation;
import com.core.cache.common.operation.CacheOperationMetadata;
import com.core.cache.common.CacheExpressionParser;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author luyi
 * 默认缓存key 生成器
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
        if (!StringUtils.isEmpty(cacheOperation.getCacheName())) {
            return Objects.requireNonNull(cacheExpressionParser.executeParse(metadata, cacheOperation.getCacheName())).toString();
        }
        //如果cacheName不存在，前缀后缀必须都配置
        if (StringUtils.isEmpty(cacheOperation.getPrefix()) || StringUtils.isEmpty(cacheOperation.getSuffix())) {
            throw new CacheAnnotationIllegalException("prefix and suffix must exist when cacheName is null");
        }
        String cachePrefix = Objects.requireNonNull(cacheExpressionParser.executeParse(metadata, cacheOperation.getPrefix())).toString();
        String cacheSuffix = Objects.requireNonNull(cacheExpressionParser.executeParse(metadata, cacheOperation.getSuffix())).toString();
        return cachePrefix + getSeparator() + cacheSuffix;
    }


    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
