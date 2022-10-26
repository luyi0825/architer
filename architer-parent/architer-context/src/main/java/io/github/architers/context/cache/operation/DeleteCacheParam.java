package io.github.architers.context.cache.operation;

/**
 * 删除：对应@DeleteOperation
 *
 * @author luyi
 */
public class DeleteCacheParam extends BaseCacheOperationParam {
    /**
     * 缓存值
     */
    private String cacheValue;

    public String getCacheValue() {
        return cacheValue;
    }

    public void setCacheValue(String cacheValue) {
        this.cacheValue = cacheValue;
    }
}
