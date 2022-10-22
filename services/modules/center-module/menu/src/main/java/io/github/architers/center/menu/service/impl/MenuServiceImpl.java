package io.github.architers.center.menu.service.impl;

import io.github.architers.center.menu.dao.SysRoleDao;
import io.github.architers.center.menu.domain.vo.MenuNodeTree;
import io.github.architers.center.menu.service.MenuService;
import io.github.architers.common.jwttoken.UserInfo;
import io.github.architers.common.jwttoken.UserInfoUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private SysRoleDao sysRoleDao;

    public List<MenuNodeTree> getMenuTreeWithPrivilege() {

        //获取角色
        UserInfo userInfo = UserInfoUtils.getUserInfo();
        System.out.println(userInfo.getUserName());
        return null;


    }
}
