package io.github.architers.context.cache.fieldconvert;

import io.github.architers.context.cache.operate.LocalCacheOperate;
import io.github.architers.context.cache.operate.RemoteCacheOperate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class OriginValueObtainSupport implements ApplicationContextAware {

    @Resource
    private LocalCacheOperate localCacheOperate;
    @Resource
    private RemoteCacheOperate remoteCacheOperate;
    private final ThreadPoolExecutor putCachePool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3000));
    private final ScheduledThreadPoolExecutor scheduledCachePool = new ScheduledThreadPoolExecutor(1);

    {   //设置最大线程数量为1，延迟双删用
        scheduledCachePool.setMaximumPoolSize(1);
    }

    private final Map<String/*转换器名称*/, IFieldOriginDataObtain<Serializable>> originValueObtainMap = new HashMap<>();


    public IFieldOriginDataObtain<Serializable> getConvertOriginValueObtain(String converter) {
        return originValueObtainMap.get(converter);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBeansOfType(IFieldOriginDataObtain.class).forEach((key, originValueObtain) -> {
            if (originValueObtainMap.putIfAbsent(originValueObtain.getConverter(), originValueObtain) != null) {
                throw new RuntimeException(originValueObtain.getConverter() + "转换器重复");
            }
        });
    }

    public Object getFromLocal(String converter, String cacheKey) {
        String cacheName = DataConvertCacheNameUtils.getCacheName(converter);
        return localCache.get(cacheName, cacheKey);
    }

    public void asyncPutLocal(String converter, String cacheKey, Object cacheValue) {
        putCachePool.execute(() -> putLocal(converter, cacheKey, cacheValue));
    }

    public void asyncPutAllLocal(String converter, Map<String, Serializable> data) {
        putCachePool.execute(() -> {
            String cacheName = DataConvertCacheNameUtils.getCacheName(converter);
            localCache.putAll(cacheName, data, null, null);
            remoteCache.putAll(cacheName, data, 60L, TimeUnit.MINUTES);
        });
    }

    public void asyncPutAllRemote(String converter, Map<String, Serializable> data) {
        putCachePool.execute(() -> {
            String cacheName = DataConvertCacheNameUtils.getCacheName(converter);
            remoteCache.putAll(cacheName, data, 60L, TimeUnit.MINUTES);
        });
    }

    public void putLocal(String converter, String cacheKey, Object cacheValue) {
        String cacheName = DataConvertCacheNameUtils.getCacheName(converter);
        localCache.put(cacheName, cacheKey, (Serializable) cacheValue, null, null);
    }

    public void asyncPutRemote(String converter, String cacheKey, Object cacheValue) {
        putCachePool.execute(() -> putRemote(converter, cacheKey, cacheValue));
    }


    public void putRemote(String converter, String cacheKey, Object cacheValue) {
        String cacheName = DataConvertCacheNameUtils.getCacheName(converter);
        //60分钟过期
        remoteCache.put(cacheName, cacheKey, (Serializable) cacheValue, 60L, TimeUnit.MINUTES);
    }

    /**
     * 驱逐缓存两次（先同步，后定时）
     */
    public void evictCacheTwoTimes(String convert, Set<String> keys) {
        String cacheName = DataConvertCacheNameUtils.getCacheName(convert);
        localCache.remove(cacheName, keys);
        remoteCache.remove(cacheName, keys);
        //延迟双删
        scheduledCachePool.schedule(() -> {
            log.info("开始定时删除缓存:{}", cacheName);
            localCache.remove(cacheName, keys);
            remoteCache.remove(cacheName, keys);
        }, 6, TimeUnit.SECONDS);
    }

    public void asyncPutLocalAndRemote(String converter, String cacheKey, Object cacheValue) {

        putCachePool.execute(() -> {
            String cacheName = DataConvertCacheNameUtils.getCacheName(converter);
            localCache.put(cacheName, cacheKey, (Serializable) cacheValue, null, null);
            //120分钟过期
            remoteCache.put(cacheName, cacheKey, (Serializable) cacheValue, 120L, TimeUnit.MINUTES);
        });

    }

    public Object getFromRemote(String converter, String cacheKey) {
        String cacheName = DataConvertCacheNameUtils.getCacheName(converter);
        return remoteCache.get(cacheName, cacheKey);
    }

    public Map<String, Serializable> getMapFromRemote(String converter, Set<String> cacheKeys) {
        String cacheName = DataConvertCacheNameUtils.getCacheName(converter);
        return remoteCache.getAll(cacheName, cacheKeys);
    }

    public Map<String, Serializable> getMapFromLocal(String converter, Set<String> keys) {
        String cacheName = DataConvertCacheNameUtils.getCacheName(converter);
        return localCache.getAll(cacheName, keys);
    }


    public Object getFromLocalOrRemote(String cacheName, String cacheKey) {
        Object cacheValue = getFromLocal(cacheName, cacheKey);
        if (cacheValue == null) {
            cacheValue = getFromRemote(cacheName, cacheKey);
        }
        return cacheValue;
    }


}
