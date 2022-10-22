package io.github.architers.center.menu.service.impl;

import io.github.architers.center.menu.dao.MenuDao;
import io.github.architers.center.menu.dao.SysRoleDao;
import io.github.architers.center.menu.domain.entity.Menu;
import io.github.architers.center.menu.domain.vo.MenuNode;
import io.github.architers.center.menu.service.MenuService;
import io.github.architers.center.menu.utils.NodeTreeUtils;
import io.github.architers.common.jwttoken.UserInfo;
import io.github.architers.common.jwttoken.UserInfoUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Resource
    private MenuDao menuDao;

    public List<NodeTreeUtils.TreeNode> getMenuTreeWithPrivilege() {
        //获取角色
        UserInfo userInfo = UserInfoUtils.getUserInfo();
        System.out.println(userInfo.getUserName());
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
}
