package com.architecture.context.cache.lock;

import com.architecture.context.exception.ServiceException;
import com.architecture.context.expression.ExpressionMetadata;

import java.util.concurrent.locks.Lock;

/**
 * @author luyi
 * 锁的执行器
 */
public class LockExecute {

    private final LockFactory lockFactory;

    public LockExecute(LockFactory lockFactory) {
        this.lockFactory = lockFactory;
    }

    public Object execute(Locked locked, ExpressionMetadata expressionMetadata, LockExecuteFunction function) throws Throwable {
        if (locked != null) {
            Lock lock = lockFactory.get(locked, expressionMetadata);
            if (LockService.FAIL_LOCK != lock) {
                try {
                    return function.execute();
                } finally {
                    if (lock != null) {
                        lock.unlock();
                    }
                }
            }
            //没有获取到锁
            throw new ServiceException("没有获取到锁");
        } else {
            return function.execute();
        }
    }
}