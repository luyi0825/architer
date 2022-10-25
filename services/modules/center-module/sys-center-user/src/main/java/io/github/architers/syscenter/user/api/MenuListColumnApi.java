package io.github.architers.syscenter.user.api;

import io.github.architers.syscenter.user.domain.entity.SysMenuList;
import io.github.architers.syscenter.user.domain.entity.SysMenuListColumn;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menuListColumnApi")
public class MenuListColumnApi {

    /**
     * 添加菜单列表
     */
    @PostMapping("/")
    public void addMenuList(SysMenuList menuList) {

    }

    /**
     * 修改系统菜单列表
     */
    public void editMenuList(SysMenuList menuList) {

    }

    /**
     * 保存配置列
     */
    public void saveMenuListColumns(List<SysMenuListColumn> listColumns) {

    }

}
