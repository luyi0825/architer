package com.lz.core.cache.common.exception;

/**
 * 缓存处理异常
 *
 * @author luyi
 */
public class CacheHandlerException extends RuntimeException {
    public CacheHandlerException(String message) {
        super(message);
    }

    public CacheHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
