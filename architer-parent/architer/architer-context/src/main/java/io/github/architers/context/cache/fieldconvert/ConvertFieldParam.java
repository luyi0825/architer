package io.github.architers.context.cache.fieldconvert;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConvertFieldParam {
    /**
     * 缓存没有命中的数据,默认为true（缓存中没有，从数据库查询后，就会将数据进行缓存）
     */
    private Boolean cacheNotHitValue;
    /**
     * 是否将本地缓存解析到临时缓存中,默认为true
     */
    private Boolean localCacheTemp;

    /**
     * 临时缓存
     */
    private TempCache tempCache;


}
