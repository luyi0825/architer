package io.github.architers.cache.caffeine;

import lombok.Data;

/**
 * @author luyi
 */
@Data
public class CaffeineConfig {

    /**
     * 最大数量
     */
    private long maximumSize = -1L;

    /**
     * 最大权重
     */
    private long maximumWeight = -1;

    /**
     * read后是否更新刷新缓存时间(默认为false)
     */
    private boolean readRefreshExpired = false;
    /**
     * 更新后是否更新刷新缓存时间(默认为false)
     */
    private boolean updateRefreshExpired = false;

    /**
     * 获取类型
     */
    private ExpireType expireType;
}
