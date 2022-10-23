package io.github.architers.center.menu.service.impl;

import io.github.architers.center.menu.dao.MenuDao;
import io.github.architers.center.menu.domain.entity.Menu;
import io.github.architers.center.menu.domain.vo.MenuNode;
import io.github.architers.center.menu.service.MenuService;
import io.github.architers.center.menu.utils.NodeTreeUtils;
import io.github.architers.common.jwttoken.UserInfo;
import io.github.architers.common.jwttoken.UserInfoUtils;
import io.github.architers.common.module.tenant.TenantUtils;
import io.github.architers.context.exception.NoLogStackException;
import io.github.architers.context.exception.ServiceException;
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
        List<Long> roleIds = userInfo.getRoles().stream().map(UserInfo.RoleInfo::getRoleId).collect(Collectors.toList());
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
    public Menu addMenu(Menu menu) {
        menu.setTenantId(TenantUtils.getTenantId());
        menu.fillCreateAndUpdateField(new Date());
        menuDao.insert(menu);
        return menu;
    }

    @Override
    public void changeStatus(Long id, Byte status) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setStatus(status);
        menu.fillCreateAndUpdateField(new Date());
        menuDao.updateById(menu);
    }

    @Override
    public void deleteMenu(Long menuId) {
        //判断子菜单
        Menu menu = menuDao.selectById(menuId);
        if (menu == null) {
            throw new NoLogStackException("删除菜单失败");
        }
        List<Menu> menus = menuDao.selectByParentCode(TenantUtils.getTenantId(), menu.getMenuCode());
        if (!CollectionUtils.isEmpty(menus)) {
            throw new NoLogStackException("请先删除子菜单");
        }
        int count = menuDao.deleteById(menuId);
        if (count != 1) {
            throw new RuntimeException("删除菜单失败");
        }
    }

    @Override
    public void editMenu(Menu edit) {
        edit.setTenantId(null);
        edit.setMenuCode(null);
        edit.fillCreateAndUpdateField(new Date());
        int count = menuDao.updateById(edit);
        if (count != 1) {
            throw new ServiceException("更新菜单失败");
        }
    }

    @Override
    public Menu getById(Long menuId) {
        return menuDao.selectById(menuId);
    }
}
