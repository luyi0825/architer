package com.lz.core.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *描述：缓存controller
 *@author luyi
 *@date 2021/4/14
 */
@RestController
@RequestMapping("/cache")
public class CacheController {
    @Autowired
    private CacheService cacheService;

    @RequestMapping("/test")
    public String test(){
      return  cacheService.getXxx("666");
    }
}
