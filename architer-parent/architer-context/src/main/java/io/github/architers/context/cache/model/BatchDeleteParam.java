package io.github.architers.context.cache.model;


import io.github.architers.context.cache.annotation.PutCache;
import lombok.Data;

import java.util.Collection;

/**
 * @author luyi
 * @see PutCache
 */
@Data
public class BatchDeleteParam implements CacheOperationParam {


    /**
     * 缓存名称
     */
    private String cacheName;

    /**
     * 缓存的key
     */
    private Collection<?> keys;

    /**
     * 是否异步
     */
    private boolean async = false;

}
