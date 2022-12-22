package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.model.DeleteParam;

/**
 * @author luyi
 * 延迟双删缓存
 */
public interface DelayDoubleDeleteCache {
    /**
     * 删除缓存
     *
     * @param deleteParam 删除的参数
     */
    void delete(DeleteParam deleteParam);

}
