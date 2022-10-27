package io.github.architers.context.cache.operation;

import io.github.architers.context.expression.ExpressionMetadata;

/**
 * @author luyi
 * key的生成器
 */
public interface KeyGenerator {

   String generator(ExpressionMetadata expressionMetadata,String cacheName);
}
