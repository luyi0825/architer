package io.github.architers.context.cache.model;

/**
 * 缓存改变参数
 *
 * @author luyi
 */
public class CacheChangeParam extends BaseCacheParam {
    /**
     * 是否异步的
     */
    protected boolean async = false;

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
}
