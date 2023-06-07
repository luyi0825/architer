package io.github.architers.context.cache.model;


import io.github.architers.context.cache.operate.CacheNameWrapper;
import io.github.architers.context.cache.operate.CacheOperate;
import lombok.Data;


/**
 * @author luyi
 * 缓存操作实体基类
 * @see org.springframework.cache.interceptor.CacheOperation 参考的这个类
 */
@Data
public abstract class BaseCacheOperationParam implements CacheOperationParam {

    /**
     * 是否异步的
     */
    private boolean async = false;

    /**
     * 缓存名称
     */
    private String cacheName;
    /**
     * 解析后的缓存key
     */
    private String key;

    /**
     * key的生成器
     */
    private CacheNameWrapper cacheNameWrapper;

    /**
     * 缓存处理器
     */
    private CacheOperate cacheOperate;


}
