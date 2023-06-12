package io.github.architers.context.cache.annotation;


import java.lang.annotation.*;

/**
 * 删除缓存
 *
 * @author luyi
 * @date 2020/12/26
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheEvict {

    /**
     * @see Cacheable#cacheName()
     */
    String cacheName() default "";


    String key() default "";

    /**
     * true表示在方法调用删除缓存
     * false 表示在方法执行之后删除
     */
    boolean beforeInvocation() default true;

    /**
     * 是否异步删除
     */
    boolean async() default false;



    /**
     * @see Cacheable#condition()
     */
    String condition() default "";

    /**
     * @see Cacheable#unless()
     */
    String unless() default "";

}
