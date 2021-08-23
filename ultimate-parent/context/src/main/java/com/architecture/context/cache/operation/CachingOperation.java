package com.architecture.context.cache.operation;

import lombok.Data;

/**
 * @author luyi
 * 多个缓存注解操作
 */
@Data
public class CachingOperation extends BaseCacheOperation {
    private BaseCacheOperation[] operations;

}
