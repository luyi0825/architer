package io.github.architers.syscenter.user.api;

import io.github.architers.syscenter.user.domain.dto.SysRoleQueryDTO;
import io.github.architers.syscenter.user.domain.entity.SysRole;
import io.github.architers.syscenter.user.service.SysRoleService;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统角色
 *
 * @author luyi
 */
@RestController
@RequestMapping("/roleApi")
public class RoleApi {

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 添加角色
     *
     * @return 角色ID
     */
    @PostMapping("/addRole")
    public Integer addRole(@RequestBody @Validated SysRole add) {
        return sysRoleService.addRole(add);
    }

    /**
     * 分页查询角色
     */
    @PostMapping("/getRolesByPage")
    public PageResult<SysRole> getRolesByPage(@RequestBody @Validated PageRequest<SysRoleQueryDTO> pageRequest) {
        return sysRoleService.getRolesByPage(pageRequest);
    }

    /**
     * 编辑角色
     */
    @PutMapping("editRole")
    public void editRole(@RequestBody @Validated SysRole edit) {
        sysRoleService.editRole(edit);
    }

}
