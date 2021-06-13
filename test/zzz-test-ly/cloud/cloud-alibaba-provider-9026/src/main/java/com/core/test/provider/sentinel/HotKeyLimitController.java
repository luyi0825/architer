package com.core.test.provider.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.core.cloud.sentinel.ResourceBlockExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 热点key限流对应的测试controller
 *
 * @author luyi
 */
@RestController
@RequestMapping("/hotKey")
public class HotKeyLimitController {

    @RequestMapping("/getX")
    @SentinelResource(value = "hotKey_getX", blockHandler = "flowHandler", blockHandlerClass = ResourceBlockExceptionHandler.class)
    public String getX(@RequestParam(name = "p1", required = false) String p1,
                       @RequestParam(name = "p2", required = false) String p2) {
        return UUID.randomUUID().toString();
    }

    public String getX_block(String p1, String p2, BlockException blockException) {
        return p1;
    }

}
