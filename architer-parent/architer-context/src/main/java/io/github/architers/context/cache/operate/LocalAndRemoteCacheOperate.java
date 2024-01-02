package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.utils.CacheUtils;
import org.springframework.util.Assert;


/**
 * 两级缓存操作
 * final不可继承，是可以二级缓存操作的标识类，并管理二级缓存的本地缓存操作和远程操作类
 *
 * @author luyi
 */
public final class LocalAndRemoteCacheOperate implements CacheOperate {


    private LocalCacheOperate localCacheOperate;

    private RemoteCacheOperate remoteCacheOperate;

    public LocalAndRemoteCacheOperate(LocalCacheOperate localCacheOperate, RemoteCacheOperate remoteCacheOperate) {
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
    public void delete(EvictParam evictParam) {
        localCacheOperate.delete(evictParam);
        remoteCacheOperate.delete(evictParam);
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
    public void deleteAll(EvictAllParam evictAllParam) {
        localCacheOperate.deleteAll(evictAllParam);
        remoteCacheOperate.deleteAll(evictAllParam);
    }

    @Override
    public void batchDelete(BatchEvictParam batchEvictParam) {
        localCacheOperate.batchDelete(batchEvictParam);
        remoteCacheOperate.batchDelete(batchEvictParam);
    }

    @Override
    public void batchPut(BatchPutParam batchPutParam) {
        localCacheOperate.batchPut(batchPutParam);
        remoteCacheOperate.batchPut(batchPutParam);
    }

    public LocalCacheOperate getLocalCacheOperate() {
        return localCacheOperate;
    }

    public LocalAndRemoteCacheOperate setLocalCacheOperate(LocalCacheOperate localCacheOperate) {
        this.localCacheOperate = localCacheOperate;
        return this;
    }

    public RemoteCacheOperate getRemoteCacheOperate() {
        return remoteCacheOperate;
    }


    public LocalAndRemoteCacheOperate setRemoteCacheOperate(RemoteCacheOperate remoteCacheOperate) {
        this.remoteCacheOperate = remoteCacheOperate;
        return this;
    }
}
