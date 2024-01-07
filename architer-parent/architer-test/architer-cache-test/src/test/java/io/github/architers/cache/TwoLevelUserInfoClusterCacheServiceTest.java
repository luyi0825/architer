package io.github.architers.cache;

import cn.hutool.core.lang.Assert;
import io.github.architers.cache.entity.UserInfo;
import io.github.architers.cache.service.ITwoLevelUserInfoClusterCacheService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.UUID;

@SpringBootTest
public class TwoLevelUserInfoClusterCacheServiceTest {

    @Resource
    private ITwoLevelUserInfoClusterCacheService twoLevelUserInfoClusterCacheService;

    @Test
    public void getUserInfoWithCache() {
        String userName = UUID.randomUUID().toString();
        UserInfo userInfo = twoLevelUserInfoClusterCacheService.getUserInfoWithCache(userName);
        Assert.isTrue(userName.equals(userInfo.getUsername()), "用户名应该相等");
    }

    @Test
    public void delete() {
        String userName = UUID.randomUUID().toString();
        twoLevelUserInfoClusterCacheService.delete(userName);

    }

}
