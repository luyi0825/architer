package io.github.architers.context.cache.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 删除：对应@DeleteOperation
 *
 * @author luyi
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeleteParam extends CacheChangeParam {

    /**
     * 缓存key
     */
    private String key;

}
