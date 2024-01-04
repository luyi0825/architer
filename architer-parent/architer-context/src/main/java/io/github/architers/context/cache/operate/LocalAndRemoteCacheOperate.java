package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.model.*;
import io.github.architers.context.cache.utils.CacheUtils;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 两级缓存操作
 * final不可继承，是可以二级缓存操作的标识类，并管理二级缓存的本地缓存操作和远程操作类
 *
 * @author luyi
 */
@Getter
public final class LocalAndRemoteCacheOperate implements CacheOperate {


    private final LocalCacheOperate localCacheOperate;

    private final RemoteCacheOperate remoteCacheOperate;

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

    @Override
    public Map<String, Serializable> batchGet(BatchGetParam batchGetParam) {
        Map<String, Serializable> returnMap = null;
        //先从本地查询
        if (localCacheOperate != null) {
            returnMap = localCacheOperate.batchGet(batchGetParam);
        }
        if (remoteCacheOperate == null) {
            //只有本地缓存，直接返回
            return returnMap;
        }
        if (!CollectionUtils.isEmpty(returnMap) && batchGetParam.getKeys().size() == returnMap.size()) {
            // 全部本地缓存获取到了
            return returnMap;
        }
        if (CollectionUtils.isEmpty(returnMap)) {
            //本地缓存没有获取到值，或者本地缓存操作为空
            return remoteCacheOperate.batchGet(batchGetParam);
        }
        // 这个时候说明只从本地缓存获取到部分的数据->需要和远程缓存的数据进行合并处理
        BatchGetParam newBatchGetParam = getBatchGetParam(batchGetParam, returnMap);
        Map<String, Serializable> remoteValueMap = remoteCacheOperate.batchGet(newBatchGetParam);
        if (!CollectionUtils.isEmpty(remoteValueMap)) {
            returnMap.putAll(remoteValueMap);
        }
        return returnMap;
    }


    private  BatchGetParam getBatchGetParam(BatchGetParam batchGetParam, Map<String, Serializable> returnMap) {
        Set<String> notExistLocalKeys = new HashSet<>(batchGetParam.getKeys().size() - returnMap.size(), 1);
        for (String key : batchGetParam.getKeys()) {
            if (!returnMap.containsKey(key)) {
                notExistLocalKeys.add(key);
            }
        }
        BatchGetParam newBatchGetParam = new BatchGetParam();
        newBatchGetParam.setKeys(notExistLocalKeys);
        newBatchGetParam.setOriginCacheName(batchGetParam.getOriginCacheName());
        newBatchGetParam.setWrapperCacheName(batchGetParam.getWrapperCacheName());
        return newBatchGetParam;
    }


}
