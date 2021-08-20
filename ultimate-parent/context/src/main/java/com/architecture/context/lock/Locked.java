package com.architecture.context.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 * 锁注解
 */
public @interface Locked {
    /**
     * 锁的类型
     */
    LockEnum lock();

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
    float expireTime() default -1F;

    /**
     * 获取锁的时间
     */
    float tryTime() default 1.5F;

    LockType lockType() default LockType.REENTRANT;


}
