package com.architecture.context.cache.key;


import com.architecture.context.cache.CacheExpressionParser;
import com.architecture.context.cache.key.KeyGenerator;
import com.architecture.context.cache.operation.CacheOperation;
import com.architecture.context.cache.operation.CacheOperationMetadata;
import com.architecture.context.common.cache.exception.CacheAnnotationIllegalException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
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
    public String[] getKey(CacheOperationMetadata metadata) {
        CacheOperation cacheOperation = metadata.getCacheOperation();
        String[] cacheNames = cacheOperation.getCacheName();
        if (ArrayUtils.isEmpty(cacheNames)) {
            throw new IllegalArgumentException("cacheNames is empty");
        }
        String cacheKey = cacheOperation.getKey();
        if (StringUtils.isEmpty(cacheKey)) {
            throw new CacheAnnotationIllegalException("cacheKey is null");
        }
        List<String> cacheNameList = new ArrayList<>(cacheNames.length);
        for (String cacheName : cacheNames) {
            String cacheSuffix = Objects.requireNonNull(cacheExpressionParser.executeParse(metadata, cacheKey)).toString();
            if (StringUtils.isEmpty(cacheName)) {
                cacheNameList.add(cacheSuffix);
            } else {
                String cachePrefix = Objects.requireNonNull(cacheExpressionParser.executeParse(metadata, cacheName)).toString();
                cacheNameList.add(cachePrefix + getSeparator() + cacheSuffix);
            }
        }
        return cacheNameList.toArray(new String[0]);
    }


    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
