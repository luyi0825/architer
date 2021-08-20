package com.architecture.context.lock;

import com.architecture.context.cache.annotation.Cacheable;

public class TestService {

    //@Cacheable(key = "", LOCK = @Locked())
    public String getById(String id) {
        return "55";
    }

}
