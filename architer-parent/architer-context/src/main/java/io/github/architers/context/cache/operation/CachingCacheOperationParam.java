package io.github.architers.context.cache.operation;


/**
 * @author luyi
 * 多个缓存注解操作
 */
public class CachingCacheOperationParam extends BaseCacheOperationParam {
    private BaseCacheOperationParam[] operations;

    public BaseCacheOperationParam[] getOperations() {
        return operations;
    }

    public void setOperations(BaseCacheOperationParam[] operations) {
        this.operations = operations;
    }
}
