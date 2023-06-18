package io.github.architers.cache;

import cn.hutool.core.lang.Assert;
import cn.hutool.extra.spring.SpringUtil;
import io.github.architers.cache.consistency.rocketmq.RocketDelayDoubleDeleteConfiguration;
import io.github.architers.cache.entity.UserInfo;
import io.github.architers.cache.service.ITwoLevelDelayEvictAgainCacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class TwoLevelDelayEvictAgainCacheServiceTest implements ApplicationContextAware {

    @Resource
    private ITwoLevelDelayEvictAgainCacheService twoLevelUserInfoClusterCacheService;

    @Test
    public void getUserInfoWithCache() {
        String userName = UUID.randomUUID().toString();
        UserInfo userInfo = twoLevelUserInfoClusterCacheService.getUserInfoWithCache(userName);
        Assert.isTrue(userName.equals(userInfo.getUsername()), "用户名应该相等");
    }

    @Test
    public void updateUserInfoWithEvict() throws InterruptedException {
        UserInfo userInfo = UserInfo.getRandomUserInfo();
        twoLevelUserInfoClusterCacheService.updateUserInfoWithEvict(userInfo);

        UserInfo cacheUserInfo = twoLevelUserInfoClusterCacheService.getUserInfoOnlyCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo == null, "用户信息应该为空");
        TimeUnit.SECONDS.sleep(100);
    }

    @Test
    public void updateUserInfo() throws InterruptedException {
        UserInfo userInfo = UserInfo.getRandomUserInfo();
        twoLevelUserInfoClusterCacheService.updateUserInfo(userInfo);

        UserInfo cacheUserInfo = twoLevelUserInfoClusterCacheService.getUserInfoOnlyCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo == null, "用户信息应该为空");
        TimeUnit.SECONDS.sleep(100);
    }

    @Test
    public void delete() {
        String userName = UUID.randomUUID().toString();
        twoLevelUserInfoClusterCacheService.delete(userName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.registerBean(RocketDelayDoubleDeleteConfiguration.class.getName(), RocketDelayDoubleDeleteConfiguration.class);
    }
}
