package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.CacheOperateFactory;
import io.github.architers.context.cache.CacheProperties;
import io.github.architers.context.cache.model.GetParam;
import io.github.architers.context.cache.model.PutParam;
import io.github.architers.context.cache.operate.meta.CachePutMetaData;

import java.util.function.Supplier;

/**
 * 缓存操作模板
 *
 * @author luyi
 */
public class CacheOperateTemplate {

    private CacheOperateFactory cacheOperateFactory;

    private CacheProperties cacheProperties;

    /**
     * 获取缓存
     *
     * @param cacheName
     * @param key
     * @return
     */
    public Object get(String cacheName, String key) {
        CacheOperate cacheOperate = cacheOperateFactory.getByCacheName(cacheName);
        GetParam getParam = new GetParam();
        getParam.setKey(key);
        getParam.setOriginCacheName(cacheName);
        getParam.setWrapperCacheName(wrapperCacheName(cacheName));
        cacheOperate.get(getParam);
        return cacheOperate.get(getParam);
    }

    public Object cacheable(String cacheName, String key, CachePutMetaData metaData, Supplier<Object> getSupper) {
        Object returnValue = get(cacheName, key);
        if (returnValue != null) {
            return returnValue;
        }
        //调用方法
        returnValue = getSupper.get();
        put(cacheName, key, returnValue, metaData);
        return returnValue;
    }

    public void put(String cacheName, String key, Object cacheValue, CachePutMetaData metaData) {
        CacheOperate cacheOperate = cacheOperateFactory.getByCacheName(cacheName);
        PutParam putParam = new PutParam();

        cacheOperate.put();
    }

    private String wrapperCacheName(String cacheName) {
        return String.join(":", cacheProperties.getNamespace(), cacheName);
    }
}
