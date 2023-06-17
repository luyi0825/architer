package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.CacheConfig;
import io.github.architers.context.cache.CacheProperties;
import io.github.architers.context.expression.ExpressionParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存操作辅助类，获取缓存操作相关的插件
 *
 * @author luyi
 */
@Slf4j
public class CacheOperateSupport implements ApplicationContextAware {


    private Map<String, CacheOperateContext> cacheOperateContextMap;

    private CacheOperateContext defaultCacheOperateContext;

    private CacheOperate defaultTowLevelCacheOperate;


    @Autowired(required = false)
    protected ExpressionParser expressionParser;

    private CacheProperties cacheProperties;

    public CacheOperateSupport() {

    }

    /**
     * 得到缓存操作
     *
     * @param originCacheName 原始的缓存名称
     * @return 不会为空
     */
    public CacheOperate getCacheOperate(String originCacheName) {
        CacheOperateContext cacheOperateContext = cacheOperateContextMap.get(originCacheName);
        if (cacheOperateContext == null) {
            return null;
        }
        return cacheOperateContext.getCacheOperate();
    }

    public CacheOperateContext getCacheOperateContext(String originCacheName) {
        CacheOperateContext cacheOperateContext = cacheOperateContextMap.get(originCacheName);
        if (cacheOperateContext == null) {
            return defaultCacheOperateContext;
        }
        return cacheOperateContext;
    }


    public CacheNameWrapper getCacheNameWrapper(String cacheName) {
        CacheOperateContext cacheOperateContext = cacheOperateContextMap.get(cacheName);
        return cacheOperateContext.getCacheNameWrapper();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        cacheProperties = applicationContext.getBean(CacheProperties.class);
        cacheOperateContextMap = new HashMap<>(cacheProperties.getCustomConfigs().size());

        //构建classCacheOperateMap，避免一直从spring容易中拿
        Map<String, CacheOperate> beanNameMap = applicationContext.getBeansOfType(CacheOperate.class);
        Map<Class<?>, CacheOperate> classCacheOperateMap = new HashMap<>(beanNameMap.size(), 1);
        beanNameMap.forEach((beanName, cacheOperate) -> classCacheOperateMap.putIfAbsent(cacheOperate.getClass(), cacheOperate));
        //默认的缓存操作class不能为空
        Class<?> defaultCacheOperateClass = cacheProperties.getDefaultOperateClass();
        Assert.notNull(defaultCacheOperateClass, "defaultCacheOperateClass不能为空");

        //校验两级缓存配置:本地缓存和远程缓存不能为空
        if (cacheProperties.isEnableTwoLevelCache()) {
            Assert.notNull(cacheProperties.getDefaultLocalOperateClass(), "当开启两级缓存,defaultLocalOperateClass配置不能为空");
            Assert.notNull(cacheProperties.getDefaultRemoteOperateClass(), "当开启两级缓存,defaultRemoteOperateClass配置不能为空");
            defaultTowLevelCacheOperate = new TwoLevelCacheOperate((LocalCacheOperate) classCacheOperateMap.get(cacheProperties.getDefaultLocalOperateClass())
                    , (RemoteCacheOperate) classCacheOperateMap.get(cacheProperties.getDefaultRemoteOperateClass()));
        }
        if (classCacheOperateMap.get(defaultCacheOperateClass) == null) {
            throw new IllegalArgumentException("默认cacheOperate不能为空");
        }

        //构建默认的缓存操作上下文
        {
            String defaultCacheName = "default";
            if (cacheProperties.getCustomConfigs().containsKey(defaultCacheName)) {
                throw new RuntimeException("缓存名称不能为default");
            }
            CacheConfig defaultCacheConfig = getCacheConfig(defaultCacheName);

            defaultCacheOperateContext = this.buildCacheOperateContext(defaultCacheConfig, defaultCacheName, applicationContext, classCacheOperateMap);
            cacheOperateContextMap.put(defaultCacheName, defaultCacheOperateContext);
        }
        //构建配置的缓存操作上下文
        {
            for (String cacheName : cacheProperties.getCustomConfigs().keySet()) {
                CacheConfig cacheConfig = getCacheConfig(cacheName);
                CacheOperateContext cacheOperateContext = this.buildCacheOperateContext(cacheConfig, cacheName, applicationContext, classCacheOperateMap);
                cacheOperateContextMap.put(cacheName, cacheOperateContext);
            }
        }


    }

    private CacheOperateContext buildCacheOperateContext(CacheConfig config,
                                                         String cacheName,
                                                         ApplicationContext applicationContext,
                                                         Map<Class<?>, CacheOperate> classCacheOperateMap) {
        CacheOperateContext cacheOperateContext = new CacheOperateContext();
        //缓存名称包装器
        if (config.getCacheNameWrapperClass() != null) {
            cacheOperateContext.setCacheNameWrapper(applicationContext.getBean(config.getCacheNameWrapperClass()));
        }
        CacheOperate cacheOperate;
        if (config.getOperateClass().equals(TwoLevelCacheOperate.class)) {//两级缓存
            if (!cacheProperties.isEnableTwoLevelCache()) {
                throw new IllegalArgumentException("未开启两级缓存");
            }
            if (config.getLocalOperateClass().equals(cacheProperties.getDefaultOperateClass())
                    && config.getRemoteOperateClass().equals(cacheProperties.getDefaultRemoteOperateClass())) {
                //默认配置
                cacheOperate = defaultTowLevelCacheOperate;
            } else {
                LocalCacheOperate localCacheOperate = (LocalCacheOperate) classCacheOperateMap.get(config.getLocalOperateClass());
                RemoteCacheOperate remoteCacheOperate = (RemoteCacheOperate) classCacheOperateMap.get(config.getRemoteOperateClass());
                cacheOperate = new TwoLevelCacheOperate(localCacheOperate, remoteCacheOperate);
            }


        } else { //非两级缓存
            cacheOperate = classCacheOperateMap.get(config.getOperateClass());
        }
        Assert.notNull(cacheOperate, cacheName + "对应的cacheOperate不能为空");
        cacheOperateContext.setCacheOperate(cacheOperate);
        cacheOperateContext.setDelayEvict(config.getChangeDelayDelete());

        return cacheOperateContext;
    }

    /**
     * 构建缓存配置
     */
    private CacheConfig getCacheConfig(String cacheName) {
        CacheConfig cacheConfig = cacheProperties.getCustomConfigs().get(cacheName);
        if (cacheConfig == null) {
            cacheConfig = new CacheConfig();
        } else {
            //不要影响原来的的属性配置
            CacheConfig copyCacheConfig = new CacheConfig();
            BeanUtils.copyProperties(cacheConfig, copyCacheConfig);
            cacheConfig = copyCacheConfig;
        }
        if (cacheConfig.getCacheNameWrapperClass() == null) {
            cacheConfig.setCacheNameWrapperClass(cacheProperties.getDefaultCacheNameWrapperClass());
        }
        if (cacheConfig.getOperateClass() == null) {
            cacheConfig.setOperateClass(cacheProperties.getDefaultOperateClass());
        }
        if (cacheConfig.getLocalOperateClass() == null) {
            cacheConfig.setLocalOperateClass(cacheProperties.getDefaultLocalOperateClass());
        }
        if (cacheConfig.getRemoteOperateClass() == null) {
            cacheConfig.setRemoteOperateClass(cacheProperties.getDefaultRemoteOperateClass());
        }
        if (cacheConfig.getChangeDelayDelete() == null) {
            cacheConfig.setChangeDelayDelete(cacheProperties.isChangeDelayDelete());
        }
        return cacheConfig;
    }

    public ExpressionParser getExpressionParser() {
        return expressionParser;
    }
}
