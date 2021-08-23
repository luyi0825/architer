package com.architecture.test.cache.delete;

import com.architecture.context.cache.annotation.DeleteCache;
import com.architecture.test.cache.UserInfo;

public interface DeleteService {
    @DeleteCache(cacheName = "'oneDelete'", key = "#userInfo.username")
    void oneDelete(UserInfo userInfo);
}
