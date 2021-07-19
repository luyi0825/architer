package com.architecture.ultimate.cache.local;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 描述：系统默认的清理缓存的线程池
 * <p>这个线程池只用于清理缓存,不设置核心线程，其他的值都设置的比较小</p>
 * <p>可以自己定义线程池替换这个默认的清理缓存的线程池 @see LocalCacheConfig # cleanCacheExecutorService方法</p>
 *
 * @author luyi
 * @date 2021/3/9
 */
public class DefaultCleanCacheExecutorServiceImpl extends ThreadPoolTaskExecutor implements CleanCacheExecutorService {

    @Override
    public void initialize() {
        super.initialize();
        this.setCorePoolSize(0);
        this.setMaxPoolSize(2);
        this.setQueueCapacity(20);
        this.setKeepAliveSeconds(5);
    }

}
