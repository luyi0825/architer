package com.core.test.hystrix.order.feign;


import org.springframework.stereotype.Component;

/**
 * @author luyi
 * Pay对应的远程调用
 */
@Component
public class PayClientCallBack implements PayClient {

    @Override
    public String getPayOK(String id) {
        return "pay Ok call back";
    }

    @Override
    public String getPayTimeOut(String id) {
        return "pay time out call back";
    }
}
