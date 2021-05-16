package com.lz.core.test.hystrix.pay;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author luyi
 */
@RestController
@Log4j2
@RequestMapping("/pay")
public class PayController {

    private PayService payService;

    @Autowired
    public PayController setPayService(PayService payService) {
        this.payService = payService;
        return this;
    }

    @RequestMapping("/hystrix/ok/{id}")
    public String getPayOK(@PathVariable(name = "id") String id) {
        log.info("getPayOK");
        return payService.getPayOK(id);
    }

    @RequestMapping("/hystrix/timeout/{id}")
    public String getPayTimeOut(@PathVariable(name = "id") String id) {
        log.info("getPayTimeOut");
        return payService.getPayTimeOut(id);

    }
}
