package com.architecture.cache.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述：
 *
 * @author luyi
 * @date 2021/3/10
 * 清理缓存执行器
 */

public class CleanCacheActuator {

    private static final Logger log = LoggerFactory.getLogger(CleanCacheActuator.class);

    private CleanCacheExecutorService cleanCacheExecutorService;

    private LocalCacheService localCacheService;

    @Autowired
    public CleanCacheActuator(CleanCacheExecutorService cleanCacheExecutorService, LocalCacheService localCacheService) {
        this.cleanCacheExecutorService = cleanCacheExecutorService;
        this.localCacheService = localCacheService;
        //开始清理缓存
        startCleanCache();
    }

    private void startCleanCache() {
        for (; ; ) {
            try {
                Thread.sleep(5_000);
                // this.e
            } catch (Exception e) {
                log.error("清理缓存失败", e);
            }

        }
    }

}
