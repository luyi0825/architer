package io.github.architers.syscenter.user.service;

import io.github.architers.syscenter.user.domain.entity.SysMenu;
import io.github.architers.syscenter.user.utils.NodeTreeUtils;

import java.util.List;

public interface MenuService {
    List<NodeTreeUtils.TreeNode> getMenuTreeWithPrivilege();

    SysMenu addMenu(SysMenu sysMenu);

    void changeStatus(Long menuId, Byte status);

    void deleteMenu(Long menuId);

    void editMenu(SysMenu edit);

    SysMenu getById(Long menuId);
}
