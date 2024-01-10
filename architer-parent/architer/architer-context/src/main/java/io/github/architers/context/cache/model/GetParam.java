package io.github.architers.context.cache.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取缓存
 *
 * @author luyi
 */
@EqualsAndHashCode(callSuper = true)
@Data

public class GetParam extends BaseCacheParam {
    private String key;


}
