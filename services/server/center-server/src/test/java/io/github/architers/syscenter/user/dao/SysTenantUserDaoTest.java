package io.github.architers.syscenter.user.dao;

import io.github.architers.server.SystemCenterServerStart;
import io.github.architers.syscenter.user.domain.entity.SysTenantUser;
import io.github.architers.syscenter.user.domain.entity.SysUser;
import io.github.architers.syscenter.user.domain.entity.SysUserRole;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SystemCenterServerStart.class)
class SysTenantUserDaoTest {

    @Resource
    private SysUserDao sysUserDao;

    @Test
    public void add() {
        List<SysUser> sysUsers = new ArrayList<>(1000);
        for (int i = 0; i < 1000_000_0; i++) {
            SysUser sysUser = new SysUser();
            sysUser.setUserName("username" + i);
            sysUser.setUserCaption("userCaption" + i);
            sysUser.setPassword(UUID.randomUUID().toString());

            sysUser.setStatus((byte) 1);
            sysUser.fillCreateAndUpdateField(new Date());
            sysUsers.add(sysUser);
            if (sysUsers.size() >= 1000) {
                sysUserDao.insertBatch(sysUsers);
                sysUsers.clear();
            }

        }
        if (!CollectionUtils.isEmpty(sysUsers)) {
            sysUserDao.insertBatch(sysUsers);
        }

    }


}