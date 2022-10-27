package io.github.architers.context.cache.operation;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author luyi
 */
public class CacheCacheOperateFactory implements ApplicationContextAware {

    private Map<Class<? extends CacheOperate>, CacheOperate> cacheManagerMap;
    private CacheOperate defaultCacheOperate;





    public CacheOperate getCacheOperate(Class<? extends CacheOperate> cacheOperateClass){
        CacheOperate cacheOperate = null;
        if (cacheOperateClass.equals(DefaultCacheOperate.class)) {
            //默认的缓存操作器
            cacheOperate = defaultCacheOperate;
        }
        if (cacheOperate == null) {
            cacheOperate = cacheManagerMap.get(cacheOperateClass);
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
        cacheManagerMap = CollectionUtils.newHashMap(beanNameCacheManagerMap.size());
        beanNameCacheManagerMap.forEach((key, value) -> cacheManagerMap.put(value.getClass(), value));
    }




}
