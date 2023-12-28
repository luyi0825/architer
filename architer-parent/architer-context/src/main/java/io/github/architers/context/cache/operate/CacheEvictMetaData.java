package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.annotation.Cacheable;

public class CacheEvictMetaData {
    /**
     * true 表示在方法调用删除缓存
     * false 表示在方法执行之后删除
     */
    private boolean beforeInvocation;

    /**
     * 是否异步删除
     */
    private boolean async;

    /**
     * @see Cacheable#condition()
     */
    private String condition;

    /**
     * @see Cacheable#unless()
     */
    private String unless;
}
