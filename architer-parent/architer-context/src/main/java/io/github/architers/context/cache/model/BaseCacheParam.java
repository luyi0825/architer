package io.github.architers.context.cache.model;


import lombok.Data;


/**
 * @author luyi
 * 缓存操作实体基类
 * @see org.springframework.cache.interceptor.CacheOperation 参考的这个类
 */
@Data
public abstract class BaseCacheParam implements CacheOperationParam {

    /**
     * 是否异步的
     */
    protected boolean async = false;

    /**
     * 原始的缓存名称
     */
    protected String originCacheName;

    /**
     * 包装后的缓存名称
     */
    protected String wrapperCacheName;


}
