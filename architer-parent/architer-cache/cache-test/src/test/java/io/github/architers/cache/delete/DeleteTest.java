//package io.github.architers.cache.delete;
//
//
//import io.github.architers.context.cache.Cache;
//import io.github.architers.cache.entity.UserInfo;
//import io.github.architers.context.cache.operation.CacheOperate;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
///**
// * @author luyi
// * 测试DeleteCache注解
// */
//@SpringBootTest
//public class DeleteTest {
//    @Autowired
//    private DeleteService deleteService;
//    @Autowired
//    private CacheOperate cacheOperate;
//
//    /**
//     * 测试一个删除注解
//     */
//    @Test
//    public void testOneDelete() {
//        int count = 1000;
//        for (int i = 0; i < count; i++) {
//            Cache cache = cacheOperate.getSimpleCache("simpleCache");
//            UserInfo userInfo = UserInfo.getRandomUserInfo();
//            cache.set("oneDelete" + "::" + userInfo.getUsername(), userInfo);
//            deleteService.oneDelete(userInfo);
//        }
//    }
//}
