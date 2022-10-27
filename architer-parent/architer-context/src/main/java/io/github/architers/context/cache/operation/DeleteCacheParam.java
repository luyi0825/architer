package io.github.architers.context.cache.operation;

import lombok.Data;

/**
 * 删除：对应@DeleteOperation
 *
 * @author luyi
 */
@Data
public class DeleteCacheParam extends BaseCacheOperationParam {

    /**
     * 是否异步
     */
    private boolean async;

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
