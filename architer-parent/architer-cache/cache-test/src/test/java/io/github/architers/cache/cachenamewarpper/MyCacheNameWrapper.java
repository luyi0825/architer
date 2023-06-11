package io.github.architers.cache.cachenamewarpper;

import io.github.architers.context.cache.operate.CacheNameWrapper;
import io.github.architers.context.expression.ExpressionMetadata;
import org.springframework.stereotype.Component;

@Component
public class MyCacheNameWrapper implements CacheNameWrapper {
    @Override
    public String getCacheName(ExpressionMetadata expressionMetadata, String oldCacheName) {
        return "prefix:" + oldCacheName;
    }
}
