package io.github.architers.cache.redisson.support;



import io.github.architers.contenxt.Symbol;
import io.github.architers.contenxt.cache.Cache;
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
