package io.github.architers.context.lock;

import io.github.architers.context.expression.ExpressionMetadata;

/**
 * 获取锁失败
 *
 * @author luyi
 */
public interface AcquireFailedHandler {

    Object handler(Locked locked, ExpressionMetadata expressionMetadata);

}
