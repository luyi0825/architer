package com.lz.core.cache.caffeine;

import lombok.Data;

/**
 * 描述： Caffeine缓存类,
 * 方便给Caffeine缓存中放数据的时候，自定义失效时间
 *
 * @author luyi
 * @date 2020/12/22
 */
@Data
public class CaffeineInfo {
    /**
     * 缓存过期的时间，-1表示不过期
     */
    private long expireTime;
    /**
     * 缓存的数据，尽量存字符串
     */
    private Object cacheData;


}
