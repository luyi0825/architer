package com.lz.core.test.hystrix.pay;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 */
@Service
@DefaultProperties(defaultFallback = "globalFallBack")
public class PayService {

    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")})
    public String getPayOK(String id) {
        try {
            TimeUnit.SECONDS.sleep((long) (Math.random() * 5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "ok_id:" + id + ",线程池:" + Thread.currentThread().getName();
    }

    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")},
            fallbackMethod = "getPayTimeOutHandler")
    public String getPayTimeOut(String id) {
        try {
            TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "timeout_id:" + id + ",线程池:" + Thread.currentThread().getName();
    }


    public String getPayTimeOutHandler(String id) {
        return "哎,超过3秒了啊，timeout_id:" + id + ",线程池:" + Thread.currentThread().getName();
    }

    /**
     * 全局的fallBack
     *
     * @return
     */
    public String globalFallBack() {
        return "globalFallBack:" + Thread.currentThread().getName();
    }
}
