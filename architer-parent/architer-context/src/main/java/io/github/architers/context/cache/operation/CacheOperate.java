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
     * @param putCacheParam 设置缓存的参数
     */
    default void put(PutCacheParam putCacheParam) {
        noSupport();
    }

    default void noSupport() {
        throw new RuntimeException("该方法暂时不支持");
    }


    ;

    /**
     * 删除缓存
     */
    default void delete(DeleteCacheParam deleteCacheParam) {
        noSupport();
    }


    default Object get(GetCacheParam getCacheParam) {
        noSupport();
        return null;
    }

    /**
     * 清理所有
     */
    default void deleteAll(DeleteCacheParam deleteCacheParam) {
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
}
