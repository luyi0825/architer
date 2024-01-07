package io.github.architers.context.lock;

import io.github.architers.context.lock.eums.LockType;
import lombok.Data;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Data
public abstract class BaseLockParam {
    /**
     * 锁的类型
     * <li>防止用户在系统中时候多种锁</li>
     */
    private LockType lockType;

    /**
     * 锁的名称（不支持EL表达式）
     */
    private String lockName;

    /**
     * 条件满足的时候，进行缓存操作
     */
    private String condition;

    /**
     * 是否公平锁：默认值是
     */
    private boolean fair;

    /**
     * 时间单位:默认秒
     */
    private TimeUnit timeUnit;

    /**
     * 尝试获取锁的时间
     */
    private long tryTime;


    /**
     * 释放锁的时间
     */
    private long leaseTime;

    /**
     * 失败后的处理器
     */
    private String failHandle;

}
