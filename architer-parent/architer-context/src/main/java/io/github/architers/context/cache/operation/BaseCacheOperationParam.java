package io.github.architers.context.cache.operation;


import io.github.architers.context.cache.annotation.Cacheable;
import lombok.Data;


/**
 * @author luyi
 * 缓存操作实体基类
 * @see org.springframework.cache.interceptor.CacheOperation 参考的这个类
 */
@Data
public abstract class BaseCacheOperationParam implements CacheOperationParam {

    /**
     * 缓存的空间
     */
    private String namespace;

    /**
     * @see Cacheable#cacheName()
     */
    private String[] cacheName;
    /**
     * 解析后的缓存key
     */
    private String cacheKey;
    /**
     * 是否异步
     * @see Cacheable#async()
     */
    private boolean async;

    /**
     * 缓存管理器
     */
    private Class<? extends CacheOperate> cacheManager;


}
