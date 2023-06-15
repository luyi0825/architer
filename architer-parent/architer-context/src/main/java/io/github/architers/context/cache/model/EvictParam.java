package io.github.architers.context.cache.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 缓存驱逐的参数
 *
 * @author luyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EvictParam extends CacheChangeParam {

    /**
     * 缓存key
     */
    private String key;

}
