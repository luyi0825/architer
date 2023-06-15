package io.github.architers.context.cache.operate;

import io.github.architers.context.cache.CacheConfig;
import io.github.architers.context.cache.CacheProperties;
import io.github.architers.context.expression.ExpressionParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存操作工厂类
 *
 * @author luyi
 */
@Slf4j
public class CacheOperateSupport implements ApplicationContextAware {


    private CacheNameWrapper defaultCacheNameWrapper;


    private Map<String/*缓存名称*/, CacheNameWrapper> cacheNameWrapperMap;

    @Resource
    private CacheOperateManager cacheOperateManager;

    @Autowired(required = false)
    protected ExpressionParser expressionParser;


    public CacheOperateSupport() {

    }

    /**
     * 得到缓存操作
     *
     * @param originCacheName 原始的缓存名称
     * @return 不会为空
     */
    public CacheOperate getCacheOperate(String originCacheName) {
        return cacheOperateManager.getCacheOperate(originCacheName);
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


    public ExpressionParser getExpressionParser() {
        return expressionParser;
    }
}
