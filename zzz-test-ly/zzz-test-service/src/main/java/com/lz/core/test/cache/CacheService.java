package com.lz.core.test.cache;

import com.lz.core.cache.common.annotation.PutCache;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    @PutCache
    public String getXxx(String s) {
        System.out.println("查询数据库");
        return s;
    }
}
