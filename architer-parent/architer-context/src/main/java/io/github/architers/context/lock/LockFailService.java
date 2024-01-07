package io.github.architers.context.lock;

import io.github.architers.context.lock.annotation.ExclusiveLock;

/**
 * @author luyi
 * 获取锁失败的流程
 */
public interface LockFailService {
    /**
     * 抛出失败的一样
     * @param lock
     */
    void throwFailException(ExclusiveLock lock);

}
