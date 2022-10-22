package io.github.architers.center.menu.api;

import io.github.architers.center.menu.domain.entity.Menu;
import io.github.architers.center.menu.service.MenuService;
import io.github.architers.center.menu.utils.NodeTreeUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加菜单
     */
    @PostMapping("/addMenu")
    public Menu addMenu(@Validated @RequestBody Menu menu) {
        return menuService.addMenu(menu);
    }

    /**
     * 改变菜单状态
     *
     * @param status 改变后状态
     * @param id     菜单ID
     */
    @PutMapping("/changeStatus/{id}")
    public void changeStatus(@PathVariable() Long id,
                             @RequestParam("status") byte status) {
        menuService.changeStatus(id, status);
    }

}
