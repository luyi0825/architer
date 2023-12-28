package io.github.architers.context.cache;

import io.github.architers.context.cache.operate.CacheOperate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheOperateFactory implements ApplicationContextAware {


    public Map<Class<? extends CacheOperate>, CacheOperate> cacheOperateMap = new ConcurrentHashMap<>();

    private Map<String/*缓存名称*/,CacheOperate> cacheNameOperateMap = new ConcurrentHashMap<>();
    /**
     * 通过缓存名称获取
     *
     * @param cacheName 缓存名称
     * @return 缓存曹操
     */
    public CacheOperate getByCacheName(String cacheName) {
        cacheNameOperateMap.get(cacheName);
    }

    public CacheOperate getCacheOperate(Class<? extends CacheOperate> cacheOperateClass) {
        return cacheOperateMap.get(cacheOperateClass);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            applicationContext.getBeansOfType(CacheOperate.class).forEach((beanName, cacheOperate) -> {

            });
        } catch (Exception e) {

        }
    }
}
