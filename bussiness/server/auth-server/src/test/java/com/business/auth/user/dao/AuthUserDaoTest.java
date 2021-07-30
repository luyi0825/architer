package com.business.auth.user.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.auth.user.entity.AuthUser;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

/**
 * 权限用户测试类
 *
 * @author luyi
 */
@SpringBootTest
@RunWith(value = SpringRunner.class)
public class AuthUserDaoTest {
    private static final Logger logger = LoggerFactory.getLogger(AuthUserDaoTest.class);

    private AuthUserDao authUserDao;


    @Test
    public void insert() {
        for (int i = 0; i < 1000; i++) {
            AuthUser user = new AuthUser();
            user.setUsername(UUID.randomUUID().toString());
            user.setPassword(UUID.randomUUID().toString());
            user.setMail("8934@qq.com");
            authUserDao.insert(user);
        }
    }

    @Test
    public void selectById() {
        AuthUser user = authUserDao.selectById(1420949375531130882L);
        logger.info(user.toString());
    }

    /**
     * 测试批量，两个ID在不同的库
     */
    @Test
    public void selectBatchIds() {
        List<AuthUser> userList = authUserDao.selectBatchIds(List.of(1420949375531130882L, 1420949232002048001L));
        Assert.assertTrue((userList != null && userList.size() == 2));
    }

    /**
     * 测试排序
     */
    @Test
    public void testOrder() {
        Long startTime = System.currentTimeMillis();
        QueryWrapper<AuthUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", List.of(1420949375531130882L, 1420949231532285953L, 1420949232002048001L));
        queryWrapper.orderByAsc("id");
        List<AuthUser> list = authUserDao.selectList(queryWrapper);
        list.forEach(authUser -> logger.info("id:{}", authUser.getId()));
        Long endTime = System.currentTimeMillis();
        logger.info("查询testOrder[{}]条数据，耗时:{}纳秒", list.size(), endTime - startTime);
    }

    /**
     * 测试查询所有
     */
    @Test
    public void selectList() {
        Long startTime = System.currentTimeMillis();
        List<AuthUser> list = authUserDao.selectList(null);
        Long endTime = System.currentTimeMillis();
        logger.info("查询list[{}]条数据，耗时:{}纳秒", list.size(), endTime - startTime);
    }

    /**
     * 测试查询数量
     */
    @Test
    public void selectCount() {
        Long startTime = System.currentTimeMillis();
        int count = authUserDao.selectCount(null);
        Long endTime = System.currentTimeMillis();
        logger.info("selectCount[{}]条数据，耗时:{}纳秒", count, endTime - startTime);
    }

    @Autowired(required = false)
    public void setAuthUserDao(AuthUserDao authUserDao) {
        this.authUserDao = authUserDao;
    }
}
