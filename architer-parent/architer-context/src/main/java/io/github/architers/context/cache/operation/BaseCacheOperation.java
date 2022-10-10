package io.github.architers.context.cache.operation;



import io.github.architers.context.cache.CacheMode;
import io.github.architers.context.cache.annotation.Cacheable;
import lombok.Data;


/**
 * @author luyi
 * 缓存操作实体基类
 * @see org.springframework.cache.interceptor.CacheOperation 参考的这个类
 */
@Data
public abstract class BaseCacheOperation implements CacheOperation {
    /**
     * 用于排序的字段顺序
     */
    private int order;
    /**
     * @see Cacheable#cacheName()
     */
    private String[] cacheName;
    /**
     * @see Cacheable#key()
     */
    private String key;
    /**
     * @see Cacheable#async()
     */
    private boolean async;


    /**
     * 条件满足的时候，进行缓存操作
     */
    private String condition;

    /**
     * 条件满足的时候，不进行缓存操作
     */
    private String unless;
    /**
     * 缓存模式
     */
    private CacheMode cacheMode;
    /**
     * 缓存管理器
     */
    private String cacheManager;


}
