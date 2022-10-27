package io.github.architers.context.cache.operation;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author 缓存key生成器工厂类
 */
public class KeyGeneratorFactory implements ApplicationContextAware {


    private Map<Class<? extends KeyGenerator>, KeyGenerator> keyGeneratorMap;

    public KeyGenerator getKeyGenerator(Class<? extends KeyGenerator> clazz) {
        return keyGeneratorMap.get(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, KeyGenerator> beanNameCacheManagerMap =
                applicationContext.getBeansOfType(KeyGenerator.class);
        keyGeneratorMap = CollectionUtils.newHashMap(beanNameCacheManagerMap.size());
        beanNameCacheManagerMap.forEach((key, value) -> keyGeneratorMap.put(value.getClass(), value));
    }
}
