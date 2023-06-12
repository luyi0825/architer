package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.CacheConfig;
import io.github.architers.context.cache.CacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存操作工厂类
 *
 * @author luyi
 */
@Slf4j
public class CacheOperateSupport implements ApplicationContextAware {

    /**
     * 避免加锁处理，用HashMap
     */
    private final Map<String, CacheOperate> cacheOperateMap = new HashMap<>();

    private CacheOperate defaultCacheOperate;

    private CacheOperate defaultTowLevelCacheOperate;

    private CacheNameWrapper defaultCacheNameWrapper;


    private Map<String/*缓存名称*/, CacheNameWrapper> cacheNameWrapperMap;


    public CacheOperateSupport() {

    }


    /**
     * 得到缓存操作
     *
     * @param originCacheName 原始的缓存名称
     * @return 不会为空
     */
    public CacheOperate getCacheOperate(String originCacheName) {
        CacheOperate cacheOperate = cacheOperateMap.get(originCacheName);
        if (cacheOperate == null) {
            //没有配置，就采用默认缓存操作配置
            return defaultCacheOperate;
        }
        //配置了就用配置的
        return cacheOperate;
    }


    public CacheNameWrapper getCacheNameWrapper(String cacheName) {
        CacheNameWrapper cacheNameWrapper = cacheNameWrapperMap.get(cacheName);
        if (cacheNameWrapper == null) {
            return defaultCacheNameWrapper;
        }
        return cacheNameWrapper;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CacheProperties cacheProperties = applicationContext.getBean(CacheProperties.class);
        //构建classCacheOperateMap，避免一直从spring容易中拿
        Map<String, CacheOperate> beanNameMap = applicationContext.getBeansOfType(CacheOperate.class);
        Map<Class<?>, CacheOperate> classCacheOperateMap = new HashMap<>(beanNameMap.size(), 1);
        beanNameMap.forEach((beanName, cacheOperate) -> classCacheOperateMap.putIfAbsent(cacheOperate.getClass(), cacheOperate));

        this.buildCacheOperateInfo(classCacheOperateMap, cacheProperties);

        this.buildCacheNameWrapperInfo(cacheProperties, applicationContext);


    }

    private void buildCacheNameWrapperInfo(CacheProperties cacheProperties, ApplicationContext applicationContext) {

        if (cacheProperties.getDefaultCacheNameWrapperClass() != null) {
            defaultCacheNameWrapper = applicationContext.getBean(cacheProperties.getDefaultCacheNameWrapperClass());
        }


        Map<String/*缓存名称*/, CacheConfig> cacheConfigMap = cacheProperties.getCustomConfigs();
        cacheNameWrapperMap = new HashMap<>(cacheConfigMap.size(), 1);
        cacheConfigMap.forEach((cacheName, config) -> {
                    Class<? extends CacheNameWrapper> cacheNameWrapperClass = config.getCacheNameWrapperClass();
                    if (cacheNameWrapperClass == null) {
                        cacheNameWrapperClass = cacheProperties.getDefaultCacheNameWrapperClass();
                    }
                    if (cacheNameWrapperClass != null) {
                        cacheNameWrapperMap.putIfAbsent(cacheName, applicationContext.getBean(cacheNameWrapperClass));
                    }
                }
        );
    }

    private void buildCacheOperateInfo(Map<Class<?>, CacheOperate> classCacheOperateMap, CacheProperties cacheProperties) {

        //校验两级缓存配置:本地缓存和远程缓存不能为空
        if (cacheProperties.isEnableTwoLevelCache()) {
            Assert.notNull(cacheProperties.getDefaultLocalOperateClass(), "当开启两级缓存,defaultLocalOperateClass配置不能为空");
            Assert.notNull(cacheProperties.getDefaultRemoteOperateClass(), "当开启两级缓存,defaultRemoteOperateClass配置不能为空");
            defaultTowLevelCacheOperate = new TwoLevelCacheOperate((LocalCacheOperate) classCacheOperateMap.get(cacheProperties.getDefaultLocalOperateClass())
                    , (RemoteCacheOperate) classCacheOperateMap.get(cacheProperties.getDefaultRemoteOperateClass()));
        }

        //默认的缓存操作不能为空
        Class<?> defaultCacheOperateClass = cacheProperties.getDefaultOperateClass();
        defaultCacheOperate = classCacheOperateMap.get(defaultCacheOperateClass);
        Assert.notNull(defaultCacheOperate, "默认cacheOperate不能为空");

        /*
         * 1.没有配置，就采用默认的
         * 2.配置了，就用配置的
         */
        cacheProperties.getCustomConfigs().forEach((cacheName, config) -> {

            Class<?> cacheOperateClass = config.getOperateClass();
            if (cacheOperateClass == null) {
                cacheOperateClass = defaultCacheOperateClass;
            }
            if (cacheOperateClass.equals(TwoLevelCacheOperate.class)) {//两级缓存
                if (!cacheProperties.isEnableTwoLevelCache()) {
                    throw new IllegalArgumentException("未开启两级缓存");
                }
                if (config.getLocalOperateClass() == null && config.getRemoteOperateClass() == null) {
                    //都没有配置，直接默认的两级缓存操作器
                    cacheOperateMap.putIfAbsent(cacheName, defaultTowLevelCacheOperate);
                } else {//配置了，就重新new TwoLevelCacheOperate
                    Class<? extends LocalCacheOperate> localCacheOperateClass = config.getLocalOperateClass();
                    if (localCacheOperateClass == null) {
                        localCacheOperateClass = cacheProperties.getDefaultLocalOperateClass();
                    }
                    Class<? extends RemoteCacheOperate> remoteCacheOperateClass = config.getRemoteOperateClass();
                    if (remoteCacheOperateClass == null) {
                        remoteCacheOperateClass = cacheProperties.getDefaultRemoteOperateClass();
                    }
                    TwoLevelCacheOperate twoLevelCacheOperate = new TwoLevelCacheOperate((LocalCacheOperate) classCacheOperateMap.get(localCacheOperateClass)
                            , (RemoteCacheOperate) classCacheOperateMap.get(remoteCacheOperateClass));
                    cacheOperateMap.putIfAbsent(cacheName, twoLevelCacheOperate);
                }
            } else { //非两级缓存
                //非两级缓存不能配置对应参数（严格限制配置的准确性）
                Assert.isNull(config.getLocalOperateClass(), cacheName + "非二级缓存不用配置LocalOperateClass参数");
                Assert.isNull(config.getRemoteOperateClass(), cacheName + "非二级缓存不用配置RemoteOperateClass参数");
                CacheOperate cacheOperate = classCacheOperateMap.get(cacheOperateClass);
                Assert.notNull(cacheOperate, cacheName + "对应的cacheOperate不能为空");
                cacheOperateMap.putIfAbsent(cacheName, cacheOperate);
            }
        });

    }
}
