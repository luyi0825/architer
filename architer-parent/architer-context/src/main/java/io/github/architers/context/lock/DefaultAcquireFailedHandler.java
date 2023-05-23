package io.github.architers.context.lock;

import io.github.architers.context.expression.ExpressionMetadata;

/**
 * 获取锁失败
 *
 * @author luyi
 */
public class DefaultAcquireFailedHandler implements AcquireFailedHandler {

    @Override
    public Object handler(Locked locked, ExpressionMetadata expressionMetadata) {
        throw new RuntimeException("acquire lock Failed");
    }

}
