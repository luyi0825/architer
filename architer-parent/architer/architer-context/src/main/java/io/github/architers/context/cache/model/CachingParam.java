package io.github.architers.context.cache.model;


/**
 * @author luyi
 * 多个缓存注解操作
 */
public class CachingParam extends BaseCacheParam {
    private BaseCacheParam[] operations;

    public BaseCacheParam[] getOperations() {
        return operations;
    }

    public void setOperations(BaseCacheParam[] operations) {
        this.operations = operations;
    }
}
