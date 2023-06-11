package io.github.architers.context.cache.operate;


import io.github.architers.context.cache.model.*;

/**
 * @author luyi
 * @version 1.0.0
 * 缓存操作,不同的中间件有不同的实现
 */
public interface CacheOperate {

    /**
     * 设置缓存
     *
     * @param putParam 设置缓存的参数
     */
    default void put(PutParam putParam) {
        noSupport();
    }

    default void noSupport() {
        throw new RuntimeException("该方法暂时不支持");
    }


    /**
     * 删除缓存
     */
    default void delete(DeleteParam deleteParam) {
        noSupport();
    }


    default Object get(GetParam getParam) {
        noSupport();
        return null;
    }

    /**
     * 清理所有
     */
    default void deleteAll(DeleteAllParam deleteParam) {
        noSupport();
    }

    /**
     * 批量删除
     */
    default void batchDelete(BatchDeleteParam batchDeleteParam) {
        noSupport();
    }

    /**
     * 批量put缓存
     */
    default void batchPut(BatchPutParam batchPutParam) {
        noSupport();
    }
}
