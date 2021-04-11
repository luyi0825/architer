package com.lz.core.cache.common.aop;

import com.lz.core.cache.common.CacheProcess;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

/**
 * @author luyi
 * @date 2020-12-20
 */
@Aspect
public class CacheAop {


    public CacheAop() {
        System.out.println("chacheAop init.....");
    }

    @Autowired
    private List<CacheProcess> cacheProcesses;

    // @Around("execution(* com.ly.*.service.impl.*.*(..))")
    // @Around("execution(* org.crazyit.app.service.impl.*.*(..))")
    //@Around("execution(* com.ly.shop.*.service.impl.*.*(..))")
    @Around(value = "@annotation(com.ly.core.cache.anntion.EnableCache)")
    public Object cached(ProceedingJoinPoint jp) throws Throwable {

        //调用方法之前处理
        for (CacheProcess cacheProcess : cacheProcesses) {
            Object cacheValue = cacheProcess.processCache(jp);
            if (cacheValue != null) {
                //说明从缓存中取到值了，直接返回
                return cacheValue;
            }
        }

        return null;

    }

    public static void main(String[] args) {

                int x,y;
        // 00000101-->  向右移动2位(正数补0，负数补1）  00000001
                x=5>>2;


      ///  >>>:二进bai右移补零操作符，左操作数的值按右操作数指定的位数右移，移动得到的空位以零填充  --00000001 ->0000000
                y=x>>>2;
                System.out.println(y);

    }


}
