package io.github.architers.context.cache.model;


import io.github.architers.context.cache.annotation.CachePut;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/**
 * @author luyi
 * @see CachePut
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BatchEvictParam extends CacheChangeParam {

    /**
     * 缓存的key
     */
    private Collection<?> keys;

    /**
     * 是否异步
     */
    private boolean async = false;

}
