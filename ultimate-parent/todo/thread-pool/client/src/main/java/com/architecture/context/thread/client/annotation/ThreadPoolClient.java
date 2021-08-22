package com.architecture.context.thread.client.annotation;

import java.lang.annotation.*;

/**
 * @author luyi
 * 该注解表示是线程池客户端，需要监控
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ThreadPoolClient {
    /**
     * 客户端标志名称，同一服务不同类不可重复
     */
    String name();

    /**
     * 中文名称
     */
    String caption();

    /**
     * 该线程监控的时间间隔，默认5分钟
     */
    long timeInterval() default 5 * 60;


}
