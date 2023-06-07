package io.github.architers.context.cache.operate;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author luyi
 */
public class CacheOperateFactory implements ApplicationContextAware {

    private Map<Class<? extends CacheOperate>, CacheOperate> cacheOperateMap;
    private final Class<? extends CacheOperate> defaultCacheOperateClass;

    public CacheOperateFactory(Class<? extends CacheOperate> defaultCacheOperateClass) {
        Assert.notNull(defaultCacheOperateClass, "defaultCacheOperateClass is null");
        this.defaultCacheOperateClass = defaultCacheOperateClass;
    }


    public CacheOperate getCacheOperate(Class<? extends CacheOperate> cacheOperateClass) {
        CacheOperate cacheOperate = null;
        if (cacheOperateClass.equals(DefaultCacheOperate.class)) {
            //默认的缓存操作器
            cacheOperate = cacheOperateMap.get(defaultCacheOperateClass);
        }
        if (cacheOperate == null) {
            cacheOperate = cacheOperateMap.get(cacheOperateClass);
        }
        if (cacheOperate == null) {
            throw new IllegalArgumentException("cacheManger is null");
        }
        return cacheOperate;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, CacheOperate> beanNameCacheManagerMap =
                applicationContext.getBeansOfType(CacheOperate.class);
        cacheOperateMap = CollectionUtils.newHashMap(beanNameCacheManagerMap.size());
        beanNameCacheManagerMap.forEach((key, value) -> cacheOperateMap.put(value.getClass(), value));
    }


}
