package io.github.architers.center.menu.api;

import io.github.architers.center.menu.domain.vo.MenuNodeTree;
import io.github.architers.center.menu.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单
 *
 * @author luyi
 */
@RestController
@RequestMapping("/api/menu")
public class MenuApi {

    @Resource
    private MenuService menuService;

    /**
     * 获取菜单树(权限内)
     */
    @GetMapping("getMenuTreeWithPrivilege")
    public List<MenuNodeTree> getMenuTreeWithPrivilege() {
        return menuService.getMenuTreeWithPrivilege();
    }

}
