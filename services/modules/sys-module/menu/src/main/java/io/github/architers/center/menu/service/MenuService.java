package io.github.architers.center.menu.service;

import io.github.architers.center.menu.domain.vo.MenuNodeTree;

import java.util.List;

public interface MenuService {
    List<MenuNodeTree> getMenuTreeWithPrivilege();
}
