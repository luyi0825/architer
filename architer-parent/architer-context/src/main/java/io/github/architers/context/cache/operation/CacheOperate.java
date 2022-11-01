package io.github.architers.context.cache.operation;


/**
 * @author luyi
 * @version 1.0.0
 * 缓存管理接口类，用于获取不同缓存的实例对象
 */
public interface CacheOperate {

    public CacheOperate DEFAULT = new CacheOperate() {
    };

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


    ;

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
    default void deleteAll(DeleteParam deleteParam) {
        noSupport();
    }

    /**
     * 批量删除
     *
     * @param batchDeleteParam
     */
    default void batchDelete(BatchDeleteParam batchDeleteParam) {
        noSupport();
    }

    default void batchPut(BatchPutParam batchPutParam) {
        noSupport();
    }
}
