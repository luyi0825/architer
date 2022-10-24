package io.github.architers.syscenter.user.api;

import io.github.architers.syscenter.user.domain.entity.Menu;
import io.github.architers.syscenter.user.service.MenuService;
import io.github.architers.syscenter.user.utils.NodeTreeUtils;
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
     * @param menuId 菜单ID
     */
    @PutMapping("/changeStatus/{menuId}")
    public void changeStatus(@PathVariable() Long menuId,
                             @RequestParam() byte status) {
        menuService.changeStatus(menuId, status);
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/deleteMenu/{menuId}")
    public void deleteMenu(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
    }


    /**
     * 更新菜单
     */
    @PutMapping("/editMenu")
    public void updateMenu(@Validated @RequestBody Menu edit) {
        menuService.editMenu(edit);
    }

    /**
     * 查询菜单详情
     */
    @GetMapping("getById/{menuId}")
    public Menu getById(@PathVariable Long menuId) {
        return menuService.getById(menuId);
    }


}
