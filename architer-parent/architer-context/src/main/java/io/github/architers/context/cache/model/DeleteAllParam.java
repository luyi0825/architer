package io.github.architers.context.cache.model;

import lombok.Data;

import java.util.Collection;

/**
 * @author Administrator
 */
@Data
public class DeleteAllParam implements CacheOperationParam {
    /**
     * 缓存名称
     */
    private String cacheName;



    /**
     * 是否异步
     */
    private boolean async = false;
}
