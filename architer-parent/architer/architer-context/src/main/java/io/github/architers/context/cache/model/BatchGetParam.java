package io.github.architers.context.cache.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * 获取缓存
 *
 * @author luyi
 */
@EqualsAndHashCode(callSuper = true)
@Data

public class BatchGetParam extends BaseCacheParam {
    private Set<String> keys;


}
