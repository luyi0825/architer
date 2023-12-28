package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.CacheEvictMetaData;
import io.github.architers.context.cache.operate.CachePutMetaData;
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
