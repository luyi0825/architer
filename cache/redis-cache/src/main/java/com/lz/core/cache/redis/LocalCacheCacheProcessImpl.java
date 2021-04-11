//package com.ly.core.cache;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//
///**
// * 本地缓存
// *
// * @author luyi
// * @date 2020/12/17
// */
//@Component
//@Slf4j
//public class LocalCacheCacheProcessImpl extends CacheProcess implements Ordered {
//
//
//
//    @Override
//    public Object processServiceBefore(ProceedingJoinPoint jp) {
//        return null;
//    }
//
//    @Override
//    boolean processServiceAfter(ProceedingJoinPoint jp, Object value) {
//
//        return false;
//    }
//
//
//    @Override
//    public int getOrder() {
//        return 20;
//    }
//}
