//package com.ly.core.cache;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * 描述：清除缓存的线程池
// * 这个定时接口，就不交给统一的定时调度服务：一是每个服务都要自己清理自己的数据，二是方式调用的服务挂了，导致不能及时清理垃圾数据
// *
// * @author luyi
// * @date 2020/12/22
// */
//@Component
//@Slf4j
//public class ClearCacheExecutor extends ScheduledThreadPoolExecutor {
//
//
//    @Autowired(required = false)
//    private CaffeineUtils caffeineUtils;
//
//    public ClearCacheExecutor() {
//        super(1);
//        this.startTimerClearCache();
//    }
//
//    /**
//     * 描述：开始定时清理缓存
//     *
//     * @author luyi
//     * @date 2020/12/22
//     */
//    private void startTimerClearCache() {
//        this.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                clearExpireData();
//            }
//        }, 100, 5 * 60, TimeUnit.SECONDS);
//
//    }
//
//    /**
//     * 描述：清理过期数据
//     *
//     * @author luyi
//     * @date 2020/12/22
//     */
//    private void clearExpireData() {
//        // Map<String,CaffeineInfo> caffeineInfoMap=
//    }
//
//
//}
