package io.github.architers.syscenter.user.service;

import io.github.architers.syscenter.user.domain.entity.Menu;
import io.github.architers.syscenter.user.utils.NodeTreeUtils;

import java.util.List;

public interface MenuService {
    List<NodeTreeUtils.TreeNode> getMenuTreeWithPrivilege();

    Menu addMenu(Menu menu);

    void changeStatus(Long menuId, Byte status);

    void deleteMenu(Long menuId);

    void editMenu(Menu edit);

    Menu getById(Long menuId);
}
