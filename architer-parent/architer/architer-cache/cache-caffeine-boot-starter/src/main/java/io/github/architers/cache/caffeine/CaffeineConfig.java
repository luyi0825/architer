package io.github.architers.cache.caffeine;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luyi
 */
@Data
public class CaffeineConfig implements Serializable {

    /**
     *
     */
    private int initialCapacity = 64;

    /**
     * 最大数量
     */
    private long maximumSize = 5000;

    /**
     * 最大权重
     */
    private long maximumWeight = -1;


    /**
     * 获取类型
     */
    private ExpireType expireType = ExpireType.none;

    /**
     * 是否打印缓存驱逐日志
     */
    private boolean printEvictLog = false;


}
