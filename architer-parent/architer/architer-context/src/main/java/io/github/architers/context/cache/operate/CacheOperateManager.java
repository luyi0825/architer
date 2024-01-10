package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.CacheConfig;
import io.github.architers.context.cache.CacheProperties;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存操作管理器
 *
 * @author luyi
 */
public class CacheOperateManager implements ApplicationContextAware {

    private LocalAndRemoteCacheOperate defaultCacheOperate;


    /**
     * 避免加锁处理，用HashMap
     */
    private final Map<String, LocalAndRemoteCacheOperate> cacheOperateMap = new HashMap<>();

    /**
     * 得到缓存操作
     *
     * @param originCacheName 原始的缓存名称
     * @return 不会为空
     */
    public LocalAndRemoteCacheOperate getCacheOperate(String originCacheName) {
        LocalAndRemoteCacheOperate cacheOperate = cacheOperateMap.get(originCacheName);
        if (cacheOperate == null) {
            //没有配置，就采用默认缓存操作配置
            return defaultCacheOperate;
        }
        //配置了就用配置的
        return cacheOperate;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CacheProperties cacheProperties = applicationContext.getBean(CacheProperties.class);
        //构建classCacheOperateMap，避免一直从spring容易中拿
        Map<String, CacheOperate> beanNameMap = applicationContext.getBeansOfType(CacheOperate.class);
        Map<Class<?>, CacheOperate> classCacheOperateMap = new HashMap<>(beanNameMap.size(), 1);
        beanNameMap.forEach((beanName, cacheOperate) -> classCacheOperateMap.putIfAbsent(cacheOperate.getClass(), cacheOperate));

        this.buildCacheOperateInfo(classCacheOperateMap, cacheProperties);
    }

    private CacheOperate getNotNullCacheOperate(Map<Class<?>, CacheOperate> classCacheOperateMap, Class<?> clazz) {
        CacheOperate cacheOperate = classCacheOperateMap.get(clazz);
        if (cacheOperate == null) {
            throw new IllegalArgumentException("cacheOperate is null:" + clazz);
        }
        return cacheOperate;
    }
    /*
     * 1.没有配置，就采用默认的
     * 2.配置了，就用配置的
     */
    private void buildCacheOperateInfo(Map<Class<?>, CacheOperate> classCacheOperateMap, CacheProperties cacheProperties) {
        defaultCacheOperate = getCacheOperate(cacheProperties, classCacheOperateMap);
        cacheProperties.getCustomConfigs().forEach((cacheName, config) -> {
            LocalAndRemoteCacheOperate cacheOperate = getCacheOperate(config, classCacheOperateMap);
            if (cacheOperate == null) {
                throw new IllegalArgumentException("localCacheOperate和remoteCacheOperate不能都为空");
            }
            cacheOperateMap.put(cacheName, cacheOperate);
        });
    }

    private LocalAndRemoteCacheOperate getCacheOperate(CacheConfig cacheConfig, Map<Class<?>, CacheOperate> classCacheOperateMap) {
        LocalCacheOperate localCacheOperate = null;
        RemoteCacheOperate remoteCacheOperate = null;
        if (cacheConfig.getLocalOperateClass() != null) {
            localCacheOperate = (LocalCacheOperate) getNotNullCacheOperate(classCacheOperateMap, cacheConfig.getLocalOperateClass());
        }
        if (cacheConfig.getRemoteOperateClass() != null) {
            remoteCacheOperate = (RemoteCacheOperate) getNotNullCacheOperate(classCacheOperateMap, cacheConfig.getRemoteOperateClass());
        }
        if (localCacheOperate == null && remoteCacheOperate == null) {
            return null;
        }
        return new LocalAndRemoteCacheOperate(localCacheOperate, remoteCacheOperate);
    }

}
