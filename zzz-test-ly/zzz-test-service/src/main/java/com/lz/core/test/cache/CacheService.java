package com.lz.core.test.cache;

import com.lz.core.cache.common.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    @Caching
    public String getXxx(String s) {
        System.out.println("查询数据库");
        return s;
    }
}
