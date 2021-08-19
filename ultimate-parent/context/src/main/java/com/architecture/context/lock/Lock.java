package com.architecture.context.common.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * 锁注解
 */
public @interface Lock {
    /**
     * 锁的类型
     */
    LockType lockType();

    /**
     * 锁的名称
     * <li>支持EL表达式,一个方法只解析一次，然后会被本地缓存下来，不再解析</li>
     */
    String lockName() default "";

    /**
     * 锁的key,支持EL表达式
     */
    String lockKey();

    /**
     * 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 过期时间：秒
     */
    double expireTime() default -1;

    /**
     * 获取锁的时间
     */
    double tryTime() default 1.5D;


}
