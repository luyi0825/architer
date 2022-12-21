package io.github.architers.context.cache.operate;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * 缓存名称包装器工厂类
 *
 * @author luyi
 */
public class CacheNameWrapperFactory implements ApplicationContextAware {


    private Map<String, CacheNameWrapper> cacheNameWrapperMap;

    public CacheNameWrapper getCacheNameWrapper(String cacheNameWrapper) {
        return cacheNameWrapperMap.get(cacheNameWrapper);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, CacheNameWrapper> beanNameCacheManagerMap =
                applicationContext.getBeansOfType(CacheNameWrapper.class);
        cacheNameWrapperMap = CollectionUtils.newHashMap(beanNameCacheManagerMap.size());
        cacheNameWrapperMap.putAll(beanNameCacheManagerMap);
    }
}
