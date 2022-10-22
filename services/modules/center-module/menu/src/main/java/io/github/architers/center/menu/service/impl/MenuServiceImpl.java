package io.github.architers.center.menu.service.impl;

import io.github.architers.center.menu.dao.MenuDao;
import io.github.architers.center.menu.domain.entity.Menu;
import io.github.architers.center.menu.domain.vo.MenuNode;
import io.github.architers.center.menu.service.MenuService;
import io.github.architers.center.menu.utils.NodeTreeUtils;
import io.github.architers.common.jwttoken.UserInfo;
import io.github.architers.common.jwttoken.UserInfoUtils;
import io.github.architers.common.module.tenant.TenantUtils;
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
}
