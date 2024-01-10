package io.github.architers.propertconfig.redisson;

/**
 * redisson注入到spring容器中的beanName
 */
public interface RedissonBeanName {

    /**
     * 缓存使用的beanName
     */
    String CACHE_BEAN_NAME = "io.github.architers.cache.redisson";

    /**
     * 锁使用的beanName
     */
    String LOCK_BEAN_NAME = "io.github.architers.lock.redisson";

}
