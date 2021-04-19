package com.lz.core.cache.common.proxy;

import com.lz.core.cache.common.CacheProcess;
import com.lz.core.cache.common.annotation.CustomEnableCaching;
import com.lz.core.cache.common.key.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * @author zhoupei
 * @create 2021/4/19
 **/
@Configuration
public abstract class AbstractCacheConfiguration implements ImportAware {

    @Nullable
    protected AnnotationAttributes customEnableCaching;

    @Nullable
    protected Supplier<CacheProcess> cacheProcessSupplier;

    @Nullable
    protected Supplier<KeyGenerator> keyGeneratorSupplier;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.customEnableCaching = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(CustomEnableCaching.class.getName(),false)
        );
        if (this.customEnableCaching == null){
            throw new IllegalArgumentException("@CustomEnableCaching is not present on importing class " + importMetadata.getClassName());
        }
    }

    @Autowired(required = false)
    void setConfigurers(Collection<CacheConfigurer> configurers){
        if (CollectionUtils.isEmpty(configurers)){
            return;
        }
        if (configurers.size() > 1){
            throw new IllegalStateException(configurers.size() + "CacheConfigurer的实现应该只有一次");
        }
        CacheConfigurer cacheConfigurer = configurers.iterator().next();
        useCacheConfigurer(cacheConfigurer);
    }

    protected void useCacheConfigurer(CacheConfigurer cacheConfigurer){
        this.keyGeneratorSupplier = cacheConfigurer::keyGenerator;
        this.cacheProcessSupplier = cacheConfigurer::cacheProcess;
    }
}
