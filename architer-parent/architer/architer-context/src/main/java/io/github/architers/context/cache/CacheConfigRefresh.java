//package io.github.architers.context.cache;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//
//
//public class CacheConfigRefresh implements ApplicationContextAware {
//    private ApplicationContext applicationContext;
//
//    void refresh() {
//        CacheProperties cacheProperties = applicationContext.getBean(CacheProperties.class);
//        new Thread(() -> {
//            CacheConfigManager.setDefaultCacheConfig(cacheProperties);
//            cacheProperties.getCustomConfigs().forEach((cacheName,config)->{
//
//            });
//        }).start();
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//}
