package io.github.architers.context.cache.model;


import io.github.architers.context.cache.annotation.PutCache;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/**
 * @author luyi
 * @see PutCache
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BatchDeleteParam extends CacheChangeParam {

    /**
     * 缓存的key
     */
    private Collection<?> keys;

    /**
     * 是否异步
     */
    private boolean async = false;

}
