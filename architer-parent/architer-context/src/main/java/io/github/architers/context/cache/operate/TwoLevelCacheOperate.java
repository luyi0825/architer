package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.CacheProperties;
import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 两级缓存操作
 * final不可继承，是可以二级缓存操作的标识类，并管理二级缓存的本地缓存操作和远程操作类
 *
 * @author luyi
 */
public final class TwoLevelCacheOperate implements CacheOperate {


    private LocalCacheOperate localCacheOperate;

    private RemoteCacheOperate remoteCacheOperate;

    public TwoLevelCacheOperate(LocalCacheOperate localCacheOperate, RemoteCacheOperate remoteCacheOperate) {
        Assert.notNull(localCacheOperate, "localCacheOperate is null");
        Assert.notNull(remoteCacheOperate, "remoteCacheOperate is null");
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
        if (CacheUtils.isExistCacheValue(cacheValue)) {
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
