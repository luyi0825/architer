package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.meta.CacheEvictMetaData;
import io.github.architers.context.cache.operate.meta.CachePutMetaData;
import lombok.Data;

import java.util.Map;

@Data
public class DyMetaDataConfig {

    /**
     * 放置缓存
     */
    Map<String, CachePutMetaData> cachePutMetaData;

    Map<String, CacheEvictMetaData> cacheEvictMetaData;




}
