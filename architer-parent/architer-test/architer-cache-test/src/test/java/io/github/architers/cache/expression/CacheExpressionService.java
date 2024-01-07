package io.github.architers.cache.expression;

import io.github.architers.cache.entity.UserInfo;
import io.github.architers.context.cache.annotation.CachePut;

public interface CacheExpressionService {
    /**
     * 1.使用共有字段
     * 2.使用共有方法
     * 3.使用私有字段
     */
    @CachePut(cacheName = "expression_1_publicField", key = "#userInfo.username", cacheValue = "#root.target.publicField")
    @CachePut(cacheName = "expression_2_publicMethod", key = "#userInfo.username", cacheValue = "#root.target.publicMethod()")
    @CachePut(cacheName = "expression_3_privateField", key = "#userInfo.username", cacheValue = "#root.fieldValue('privateField')")
    void root(UserInfo userInfo);
}
