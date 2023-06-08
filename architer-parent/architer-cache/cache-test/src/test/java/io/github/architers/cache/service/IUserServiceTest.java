package io.github.architers.cache.service;

import io.github.architers.cache.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@SpringBootTest
class IUserServiceTest {

    @Resource
    private IUserService userService;

    @Test
    void saveNotExpired() {
        UserInfo userInfo = UserInfo.getRandomUserInfo();
        userService.saveNotExpired(userInfo);
        UserInfo cacheUserInfo = userService.findByUsernameByCache(userInfo.getUsername());
        Assert.notNull(cacheUserInfo, "获取用户为空");
        Assert.isTrue(userInfo.getUsername().equals(cacheUserInfo.getUsername()), "用户名不相等");
    }

    @Test
    void saveExpiredWithFiveMinute() throws InterruptedException {
        UserInfo userInfo = UserInfo.getRandomUserInfo();
        userService.saveExpiredWithFiveMinute(userInfo);


        UserInfo cacheUserInfo = userService.findByUsernameByCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo != null, "用户信息为空");

    }

    @Test
    void saveExpiredWithFiveSecond() throws InterruptedException {
        UserInfo userInfo = UserInfo.getRandomUserInfo();
        userService.saveExpiredWithFiveSecond(userInfo);

        TimeUnit.SECONDS.sleep(3);
        UserInfo cacheUserInfo = userService.findByUsernameByCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo != null, "用户信息为空");
        TimeUnit.SECONDS.sleep(2);
        cacheUserInfo = userService.findByUsernameByCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo == null, "用户信息不为空");
    }

    @Test
    void findByUsername() {
        String userName = UUID.randomUUID().toString();
        UserInfo userInfo1 = userService.findByUsername(userName);
        UserInfo userInfo2 = userService.findByUsername(userName);
        Assert.isTrue( userInfo1.getUsername().equals(userInfo2.getUsername()),"用户名不相等");
    }

    @Test
    void findByUsernameExpiredWithFiveSecond() throws InterruptedException {
        String userName = UUID.randomUUID().toString();
        UserInfo userInfo = userService.findByUsernameExpiredWithFiveSecond(userName);
        TimeUnit.SECONDS.sleep(3);
        UserInfo cacheUserInfo = userService.findByUsernameByCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo != null, "用户信息为空");
        TimeUnit.SECONDS.sleep(2);
        cacheUserInfo = userService.findByUsernameByCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo == null, "用户信息不为空");
    }
}