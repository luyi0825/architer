package io.github.architers.cache.cachenamewarpper;

import cn.hutool.extra.spring.SpringUtil;
import io.github.architers.cache.entity.UserInfo;
import io.github.architers.cache.service.remote.IRemoteUserInfoCache;
import io.github.architers.context.cache.operate.CacheNameWrapper;
import io.github.architers.context.expression.ExpressionMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;
import javax.swing.*;

/**
 * 缓存名称wrapper测试
 */
@SpringBootTest
public class CacheNameWrapperTest {

    @Resource
    private IRemoteUserInfoCache remoteUserInfoCache;
    @Test
    public void cacheNameWrapperPrefix() {
        remoteUserInfoCache.putCacheNeverExpire(UserInfo.getRandomUserInfo());
    }


}
