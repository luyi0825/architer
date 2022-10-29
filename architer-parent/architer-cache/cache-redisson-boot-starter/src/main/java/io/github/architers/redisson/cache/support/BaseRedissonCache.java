package io.github.architers.redisson.cache.support;



import io.github.architers.context.Symbol;
import io.github.architers.context.cache.Cache;
import org.springframework.util.Assert;

/**
 * @author luyi
 * redis缓存基类
 */
public abstract class BaseRedissonCache implements Cache {
    /**
     * 缓存名称
     */
    protected final String cacheName;

    public BaseRedissonCache(String cacheName) {
        Assert.notNull(cacheName, "缓存名称不能为空");
        this.cacheName = cacheName;
    }

    protected String getCacheKey(Object key) {
        return String.join(Symbol.COLON, cacheName, key.toString());
    }

    @Override
    public String getCacheName() {
        return cacheName;
    }

}
