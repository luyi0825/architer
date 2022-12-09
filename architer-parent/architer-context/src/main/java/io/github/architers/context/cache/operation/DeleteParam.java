package io.github.architers.context.cache.operation;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 删除：对应@DeleteOperation
 *
 * @author luyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteParam extends BaseCacheOperationParam {

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
