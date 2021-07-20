package com.architecture.ultimate.cache.common.operation;

import lombok.Data;

/**
 * @author luyi
 * 多个缓存注解操作
 */
@Data
public class CachingOperation extends CacheOperation {
    private CacheOperation[] operations;

}
