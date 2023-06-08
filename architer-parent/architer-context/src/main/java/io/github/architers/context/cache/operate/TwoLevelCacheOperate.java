package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.model.*;

/**
 * 两级缓存操作
 *
 * @author luyi
 */
public class TwoLevelCacheOperate implements CacheOperate {

    private LocalCacheOperate localCacheOperate;

    private RemoteCacheOperate remoteCacheOperate;

    public TwoLevelCacheOperate(LocalCacheOperate localCacheOperate, RemoteCacheOperate remoteCacheOperate) {
        this.localCacheOperate = localCacheOperate;
        this.remoteCacheOperate = remoteCacheOperate;
    }


    @Override
    public void put(PutParam putParam) {
        localCacheOperate.put(putParam);
        remoteCacheOperate.put(putParam);
    }

    @Override
    public void delete(DeleteParam deleteParam) {
        localCacheOperate.delete(deleteParam);
        remoteCacheOperate.delete(deleteParam);
    }

    @Override
    public Object get(GetParam getParam) {
        Object cacheValue = localCacheOperate.get(getParam);
        if (cacheValue != null) {
            return cacheValue;
        }
        return remoteCacheOperate.get(getParam);
    }

    @Override
    public void deleteAll(DeleteAllParam deleteAllParam) {
        localCacheOperate.deleteAll(deleteAllParam);
        remoteCacheOperate.deleteAll(deleteAllParam);
    }

    @Override
    public void batchDelete(BatchDeleteParam batchDeleteParam) {
        localCacheOperate.batchDelete(batchDeleteParam);
        remoteCacheOperate.batchDelete(batchDeleteParam);
        CacheOperate.super.batchDelete(batchDeleteParam);
    }

    @Override
    public void batchPut(BatchPutParam batchPutParam) {
        localCacheOperate.batchPut(batchPutParam);
        remoteCacheOperate.batchPut(batchPutParam);
    }

    public LocalCacheOperate getLocalCacheOperate() {
        return localCacheOperate;
    }

    public TwoLevelCacheOperate setLocalCacheOperate(LocalCacheOperate localCacheOperate) {
        this.localCacheOperate = localCacheOperate;
        return this;
    }

    public RemoteCacheOperate getRemoteCacheOperate() {
        return remoteCacheOperate;
    }

    public TwoLevelCacheOperate setRemoteCacheOperate(RemoteCacheOperate remoteCacheOperate) {
        this.remoteCacheOperate = remoteCacheOperate;
        return this;
    }
}
