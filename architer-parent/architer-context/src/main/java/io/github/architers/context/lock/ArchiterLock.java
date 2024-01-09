package io.github.architers.context.lock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public interface ArchiterLock extends Lock {

    /**
     * 获取锁
     *
     * @param timeout   获取锁的超时时间(-1表示不超时)
     * @param leaseTime 锁释放的时间（避免发生死锁）
     * @return 是否获取到锁
     */
    boolean tryLock(long timeout, long leaseTime, TimeUnit unit) throws InterruptedException;
}
