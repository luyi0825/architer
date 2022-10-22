package io.github.architers.center.menu.api;

import io.github.architers.center.menu.service.MenuService;
import io.github.architers.center.menu.utils.NodeTreeUtils;
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
@RequestMapping("/menuApi")
public class MenuApi {

    @Resource
    private MenuService menuService;

    /**
     * 获取菜单树(权限内)
     */
    @GetMapping("/getMenuTreeWithPrivilege")
    public List<NodeTreeUtils.TreeNode> getMenuTreeWithPrivilege() {
        return menuService.getMenuTreeWithPrivilege();
    }

}
