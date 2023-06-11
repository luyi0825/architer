package io.github.architers.cache;


import io.github.architers.cache.entity.UserInfo;
import io.github.architers.cache.service.UserInfoService;
import io.github.architers.context.Symbol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 本地缓存测试
 */
@SpringBootTest
public class CacheTest implements ApplicationContextAware {

    @Resource(name = "localUserInfoCache")
    private UserInfoService localUserInfoCache;

    private UserInfoService userInfoService;

    @Resource(name = "remoteUserInfoCache")
    private UserInfoService remoteUserInfoCache;

    @Resource(name="twoLevelCacheUserInfoCache")
    private UserInfoService twoLevelCacheUserInfoCache;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        userInfoService = twoLevelCacheUserInfoCache;
    }


    @Test
    public void putCacheNeverExpire() throws InterruptedException {
        UserInfo userInfo = UserInfo.getRandomUserInfo();
        userInfoService.putCacheNeverExpire(userInfo);
        Thread.sleep((long) (Math.random() * 5000));
        UserInfo cacheUserInfo = userInfoService.getOnlyInCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo != null && cacheUserInfo.equals(userInfo), "获取的值应该相等");
    }


    @Test
    public void putLocalCacheExpireWithFiveSecond() throws InterruptedException {
        UserInfo userInfo = UserInfo.getRandomUserInfo();
        UserInfo cacheUserInfo = userInfoService.getOnlyInCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo == null, "不应获取到缓存值");
        long startTime = System.currentTimeMillis();
        userInfoService.putCacheExpireWithFiveSecond(userInfo);

        //立即获取
        cacheUserInfo = userInfoService.getOnlyInCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo != null && cacheUserInfo.equals(userInfo), "获取的值应该相等");
        do {
            cacheUserInfo = userInfoService.getOnlyInCache(userInfo.getUsername());
        } while (cacheUserInfo != null);
        long time = System.currentTimeMillis() - startTime;
        Assert.isTrue(time > 5000 && time <= 6000, "过期时间为五秒");
        System.out.println("过期时间:" + (System.currentTimeMillis() - startTime));
//        //3秒后获取
//        Thread.sleep(3000);
//        System.out.println(System.currentTimeMillis() - startTime);
//        cacheUserInfo = localCacheService.getLocalOnlyInCache(userInfo.getUsername());
//        System.out.println(System.currentTimeMillis() - startTime);
//        Assert.isTrue(cacheUserInfo != null && cacheUserInfo.equals(userInfo), "获取的值应该相等");
//        //2秒后获取
//        Thread.sleep(2000);
//        cacheUserInfo = localCacheService.getLocalOnlyInCache(userInfo.getUsername());
//        Assert.isTrue(cacheUserInfo == null, "不应获取到缓存值");
    }

    @Test
    public void getLocalOnlyInCache() {
        UserInfo userInfo = UserInfo.getRandomUserInfo();
        UserInfo cacheUserInfo = userInfoService.getOnlyInCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo == null, "不应获取到缓存值");
        userInfoService.putCacheNeverExpire(userInfo);
        cacheUserInfo = userInfoService.getOnlyInCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo != null, "应获取到缓存值");
        Assert.isTrue(cacheUserInfo.equals(userInfo), "获取的值应该相等");
    }

    @Test
    public void getLocalNeverExpire() throws InterruptedException {
        String userName = UUID.randomUUID().toString();
        UserInfo userInfo = userInfoService.getNeverExpire(userName);
        Thread.sleep((long) (Math.random() * 5000));

        UserInfo cacheUserInfo = userInfoService.getOnlyInCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo != null && cacheUserInfo.getUsername().equals(userInfo.getUsername()), "获取的用户名应该相等");
    }


    @Test
    public void getLocalExpireWithFiveSecond() throws InterruptedException {
        String userName = UUID.randomUUID().toString();
        UserInfo userInfo = userInfoService.getExpireWithFiveSecond(userName);
        Thread.sleep(5000);

        UserInfo cacheUserInfo = userInfoService.getOnlyInCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo == null, "缓存应该过期");
    }


    @Test
    public void delete() {
        UserInfo userInfo = UserInfo.getRandomUserInfo();
        userInfoService.putCacheNeverExpire(userInfo);
        userInfoService.delete(userInfo.getUsername());
        UserInfo cacheUserInfo = userInfoService.getOnlyInCache(userInfo.getUsername());
        Assert.isTrue(cacheUserInfo == null, "缓存应该过期");
    }

    /**
     * 集合批量put
     */
    @Test
    public void collectionBatchPutNeverExpire() {

        UserInfo userInfo1 = UserInfo.getRandomUserInfo();
        UserInfo userInfo2 = UserInfo.getRandomUserInfo();

        userInfoService.collectionBatchPutNeverExpire(Arrays.asList(userInfo1, userInfo2));
        UserInfo cacheUserInfo = userInfoService.getOnlyInCache(userInfo1.getUsername() + Symbol.COLON + userInfo1.getPhone());
        Assert.isTrue(cacheUserInfo != null, "缓存不能为空");

        cacheUserInfo = userInfoService.getOnlyInCache(UUID.randomUUID().toString());
        Assert.isTrue(cacheUserInfo == null, "缓存应该不存在");
    }

    @Test
    public void mapBatchPutNeverExpire() {
        UserInfo userInfo1 = UserInfo.getRandomUserInfo();
        UserInfo userInfo2 = UserInfo.getRandomUserInfo();
        Map<String, UserInfo> userMap = Stream.of(userInfo1, userInfo2).collect(Collectors.toMap(UserInfo::getUsername, e -> e));
        userInfoService.mapBatchPutNeverExpire(userMap);
        UserInfo cacheUserInfo = userInfoService.getOnlyInCache(userInfo1.getUsername());
        Assert.isTrue(cacheUserInfo != null, "缓存不能为空");

        cacheUserInfo = userInfoService.getOnlyInCache(UUID.randomUUID().toString());
        Assert.isTrue(cacheUserInfo == null, "缓存应该不存在");
    }



}
