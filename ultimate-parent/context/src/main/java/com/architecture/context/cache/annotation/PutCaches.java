package com.architecture.context.cache.annotation;


import com.architecture.context.cache.CacheConstants;
import com.architecture.context.lock.LockEnum;

import java.lang.annotation.*;

/**
 * 向缓存中放数据
 *
 * @author luyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PutCaches {

    PutCache[] value();
}
