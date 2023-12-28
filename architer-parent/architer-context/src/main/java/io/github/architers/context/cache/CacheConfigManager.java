package io.github.architers.context.cache;

import io.github.architers.context.cache.enums.CacheLevel;
import io.github.architers.context.cache.operate.CacheOperate;
import io.github.architers.context.cache.operate.LocalCacheOperate;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存配置管理
 *
 * @author luyi
 */
public class CacheConfigManager {


    private static final Map<String, CacheConfig> cacheConfigMap = new ConcurrentHashMap<>();

    private static volatile CacheConfig defalutCacheConfig;

    /**
     * 设置默认的缓存配置
     */
    public static void setDefaultCacheConfig(CacheConfig cacheConfig) {
        checkCacheConfig("default", cacheConfig);
        defalutCacheConfig = cacheConfig;
    }


    public static CacheConfig getConfig(String cacheName) {
        CacheConfig cacheConfig = cacheConfigMap.get(cacheName);
        if (cacheConfig == null) {
            return defalutCacheConfig;
        }
        return cacheConfig;
    }

    /**
     * 覆盖默认的的配置的属性
     */
    public static void propertyOverDefault(String cacheName, CacheConfig customeConfig) {
        checkCacheConfig("default", defalutCacheConfig);
        CacheConfig allConfig = new CacheConfig();
        if (customeConfig.getCacheLevel() != null) {
            allConfig.setCacheLevel(customeConfig.getCacheLevel());
        } else {
            allConfig.setCacheLevel(defalutCacheConfig.getCacheLevel());
        }
        //changeDelayDeleteAgain
        if (customeConfig.getChangeDelayDeleteAgain() != null) {
            allConfig.setChangeDelayDeleteAgain(customeConfig.getChangeDelayDeleteAgain());
        } else {
            allConfig.setChangeDelayDeleteAgain(defalutCacheConfig.getChangeDelayDeleteAgain());
        }
        //本地缓存
        if (customeConfig.getLocalOperateClass() != null) {
            allConfig.setLocalOperateClass(customeConfig.getLocalOperateClass());
        } else {
            allConfig.setLocalOperateClass(defalutCacheConfig.getLocalOperateClass());
        }
        //远程缓存
        if (customeConfig.getRemoteOperateClass() != null) {
            allConfig.setRemoteOperateClass(customeConfig.getRemoteOperateClass());
        } else {
            allConfig.setRemoteOperateClass(defalutCacheConfig.getRemoteOperateClass());
        }
        checkCacheConfig(cacheName, allConfig);
        updateConfig(cacheName, allConfig);

    }

    public static void updateConfig(String cacheName, CacheConfig cacheConfig) {
        checkCacheConfig(cacheName, cacheConfig);
        cacheConfigMap.put(cacheName, cacheConfig);
    }

    private static void checkCacheConfig(String cacheName, CacheConfig cacheConfig) {
        Assert.notNull(cacheConfig, "cacheConfig不能为空");
        Assert.hasText(cacheName, "cacheName不能为空");
        if (cacheConfig.getCacheLevel() == null) {
            throw new IllegalArgumentException("缓存级别不能为空:" + cacheName);
        }
        if (CacheLevel.local.equals(cacheConfig.getCacheLevel())) {
            checkLocalOperateClassNotNull(cacheName, cacheConfig);
        } else if (CacheLevel.remote.equals(cacheConfig.getCacheLevel())) {
            checkRemoteOperateClassNotNull(cacheName, cacheConfig);
        } else if (CacheLevel.localAndRemote.equals(cacheConfig.getCacheLevel())) {
            checkLocalOperateClassNotNull(cacheName, cacheConfig);
            checkRemoteOperateClassNotNull(cacheName, cacheConfig);
        }
    }

    private static void checkLocalOperateClassNotNull(String cacheName, CacheConfig cacheConfig) {
        if (cacheConfig.getLocalOperateClass() == null) {
            throw new IllegalArgumentException("localOperateClass is null:" + cacheName);
        }
    }

    private static void checkRemoteOperateClassNotNull(String cacheName, CacheConfig cacheConfig) {
        if (cacheConfig.getRemoteOperateClass() == null) {
            throw new IllegalArgumentException("remoteOperateClass is null:" + cacheName);
        }
    }


}
