package io.github.architers.context.cache.operate;

import io.github.architers.context.expression.ExpressionMetadata;

/**
 * @author luyi
 */
public class DefaultCacheNameWrapper implements CacheNameWrapper {
    @Override
    public String getCacheName(ExpressionMetadata expressionMetadata, String oldCacheName) {
        return oldCacheName;
    }
}
