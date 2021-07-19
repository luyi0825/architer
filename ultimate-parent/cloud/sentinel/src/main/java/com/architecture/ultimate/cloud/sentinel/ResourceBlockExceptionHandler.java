package com.core.cloud.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.core.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 对@SentinelResource默认控流处理
 *
 * @author luyi
 */
public class ResourceBlockExceptionHandler {

    public static String flowHandler(BlockException blockException) {
        Map<String, String> map = new HashMap<>(2);
        map.put("code", "405");
        map.put("message", "稍后再试a");
        return JsonUtils.toJsonString(map);
    }

}
