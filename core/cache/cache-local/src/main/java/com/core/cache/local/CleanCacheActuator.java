package com.core.cache.local;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 描述：
 *
 * @author luyi
 * @date 2021/3/10
 * 清理缓存执行器
 */
@Log4j2
public class CleanCacheActuator {


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
