package io.github.architers.context.cache.operate.meta;

import io.github.architers.context.cache.annotation.Cacheable;
import io.github.architers.context.cache.enums.CacheLevel;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public class CachePutMetaData {

    /**
     * 缓存级别
     */
    private CacheLevel cacheLevel;

    /**
     * @see Cacheable#randomTime()
     */
   private long randomTime;


    /**
     * @see Cacheable#expireTime()
     */
   private long expireTime;

    /**
     * @see Cacheable#timeUnit()
     */
   private TimeUnit timeUnit;

    /**
     * 异步操作
     */
   private boolean async;

    /**
     * @see Cacheable#condition()
     */
   private String condition;

    /**
     * @see Cacheable#unless()
     */
   private String unless;


}
