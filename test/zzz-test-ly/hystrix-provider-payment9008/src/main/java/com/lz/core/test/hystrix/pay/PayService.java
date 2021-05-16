package com.lz.core.test.hystrix.pay;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author luyi
 */
@Service
public class PayService {


    public String getPayOK(String id) {
        return "ok_id:" + id + ",线程池:" + Thread.currentThread().getName();
    }

    public String getPayTimeOut(String id) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "timeout_id:" + id + ",线程池:" + Thread.currentThread().getName();

    }
}
