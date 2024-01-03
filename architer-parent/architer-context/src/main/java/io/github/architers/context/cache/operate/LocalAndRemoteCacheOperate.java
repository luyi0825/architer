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
        if (localCacheOperate == null && remoteCacheOperate == null) {
            throw new IllegalArgumentException("localCacheOperate和remoteCacheOperate不能同时为空");
        }
        this.localCacheOperate = localCacheOperate;
        this.remoteCacheOperate = remoteCacheOperate;
    }

    @Override
    public void put(PutParam putParam) {
        if (localCacheOperate != null) {
            localCacheOperate.put(putParam);
        }
        if (remoteCacheOperate != null) {
            remoteCacheOperate.put(putParam);
        }
    }

    @Override
    public void delete(EvictParam evictParam) {
        if (localCacheOperate != null) {
            localCacheOperate.delete(evictParam);
        }
        if (remoteCacheOperate != null) {
            remoteCacheOperate.delete(evictParam);
        }
    }


    @Override
    public Object get(GetParam getParam) {
        if (localCacheOperate != null) {
            Object cacheValue = localCacheOperate.get(getParam);
            if (CacheUtils.isExistCacheValue(cacheValue)) {
                return cacheValue;
            }
        }
        if (remoteCacheOperate != null) {
            return remoteCacheOperate.get(getParam);
        }
        return null;
    }

    @Override
    public void deleteAll(EvictAllParam evictAllParam) {
        if (localCacheOperate != null) {
            localCacheOperate.deleteAll(evictAllParam);
        }
        if (remoteCacheOperate != null) {
            remoteCacheOperate.deleteAll(evictAllParam);
        }
    }

    @Override
    public void batchDelete(BatchEvictParam batchEvictParam) {
        if (localCacheOperate != null) {
            localCacheOperate.batchDelete(batchEvictParam);
        }
        if (remoteCacheOperate != null) {
            remoteCacheOperate.batchDelete(batchEvictParam);
        }
    }

    @Override
    public void batchPut(BatchPutParam batchPutParam) {
        if (localCacheOperate != null) {
            localCacheOperate.batchPut(batchPutParam);
        }
        if (remoteCacheOperate != null) {
            remoteCacheOperate.batchPut(batchPutParam);
        }
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