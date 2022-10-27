package io.github.architers.context.cache.operation;

import io.github.architers.context.expression.ExpressionMetadata;

public class DefaultkeyGenerator implements KeyGenerator {
    public String generator(ExpressionMetadata expressionMetadata, String cacheName, String key) {

        return key;
    }
}
