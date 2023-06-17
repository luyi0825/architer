package io.github.architers.syscenter.user.service.impl;

import io.github.architers.context.exception.BusException;
import io.github.architers.syscenter.user.dao.MenuDao;
import io.github.architers.syscenter.user.domain.entity.SysMenu;
import io.github.architers.syscenter.user.domain.vo.MenuNode;
import io.github.architers.syscenter.user.service.MenuService;
import io.github.architers.syscenter.user.utils.NodeTreeUtils;
import io.github.architers.common.jwttoken.RoleInfo;
import io.github.architers.common.jwttoken.UserInfo;
import io.github.architers.common.jwttoken.UserInfoUtils;
import io.github.architers.common.module.tenant.TenantUtils;
import io.github.architers.context.exception.BusErrorException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单对应的service实现类
 *
 * @author luyi
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuDao menuDao;

    public List<NodeTreeUtils.TreeNode> getMenuTreeWithPrivilege() {
        //获取角色
        UserInfo userInfo = UserInfoUtils.getUserInfo();
        List<Long> roleIds = userInfo.getRoles().stream().map(RoleInfo::getRoleId).collect(Collectors.toList());
        //获取角色菜单
        List<MenuNode> menus = menuDao.selectByRoleIds(roleIds);
        //列表转换为树
        if (!CollectionUtils.isEmpty(menus)) {
            return NodeTreeUtils.convertToTree(menus, "parentCode", menu -> {
                NodeTreeUtils.TreeNode treeNode = new NodeTreeUtils.TreeNode();
                treeNode.setData(menu);
                treeNode.setCode(menu.getMenuCode());
                treeNode.setCaption(menu.getMenuCaption());
                treeNode.setParentCode(menu.getParentCode());
                return treeNode;
            });
        }
        return null;
    }

    @Override
    public SysMenu addMenu(SysMenu sysMenu) {
        sysMenu.setTenantId(TenantUtils.getTenantId());
        sysMenu.fillCreateAndUpdateField(new Date());
        menuDao.insert(sysMenu);
        return sysMenu;
    }

    @Override
    public void changeStatus(Long id, Byte status) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(id);
        sysMenu.setStatus(status);
        sysMenu.fillCreateAndUpdateField(new Date());
        menuDao.updateById(sysMenu);
    }

    @Override
    public void deleteMenu(Long menuId) {
        //判断子菜单
        SysMenu sysMenu = menuDao.selectById(menuId);
        if (sysMenu == null) {
            throw new BusException("删除菜单失败");
        }
        List<SysMenu> sysMenus = menuDao.selectByParentCode(TenantUtils.getTenantId(), sysMenu.getMenuCode());
        if (!CollectionUtils.isEmpty(sysMenus)) {
            throw new BusException("请先删除子菜单");
        }
        int count = menuDao.deleteById(menuId);
        if (count != 1) {
            throw new RuntimeException("删除菜单失败");
        }
    }

    @Override
    public void editMenu(SysMenu edit) {
        edit.setTenantId(null);
        edit.setMenuCode(null);
        edit.fillCreateAndUpdateField(new Date());
        int count = menuDao.updateById(edit);
        if (count != 1) {
            throw new BusErrorException("更新菜单失败");
        }
    }

    @Override
    public SysMenu getById(Long menuId) {
        return menuDao.selectById(menuId);
    }
}
