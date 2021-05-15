package com.lz.core.test.pay;

import com.lz.core.service.response.BaseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyi
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/get/{id}")
    public BaseResponse get(@PathVariable(name = "id") String id) {
        return BaseResponse.ok(id + "xx:" + port);
    }

}
