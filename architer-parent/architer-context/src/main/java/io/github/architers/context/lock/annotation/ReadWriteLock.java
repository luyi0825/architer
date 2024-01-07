package io.github.architers.context.lock.annotation;

import io.github.architers.context.lock.eums.LockType;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 读写锁
 *
 * @author luyi
 * @since 1.0.3
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ReadWriteLock {
    /**
     * 锁的类型
     * <li>防止用户在系统中时候多种锁</li>
     */
    LockType lock() default LockType.DEFAULT;

    /**
     * 条件满足的时候，进行缓存操作
     */
    String condition() default "";

    /**
     * 是否公平锁：默认值是
     */
    boolean fair() default true;

    /**
     * 锁的名称（不支持EL表达式）
     */
    String lockName();

    /**
     * 锁的key,支持EL表达式
     */
    String key() default "";

    /**
     * 时间单位:默认秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 尝试获取锁的时间
     */
    long tryTime() default 10L;

    /**
     * 释放锁的时间
     */
    long leaseTime() default 30L;

    /**
     * 获取不到锁处理的逻辑,两种写法：<br>
     * 1.beanName#methodName<br>
     * 2.methodName<br>
     * <br>
     * 如果不为empty,就会执行对应方法（注意：方法参数必须和加锁的方法保持一致）
     */
    String failHandle() default "";


}
