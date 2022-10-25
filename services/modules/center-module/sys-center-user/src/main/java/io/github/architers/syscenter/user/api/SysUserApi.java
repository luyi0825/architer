package io.github.architers.syscenter.user.api;

import io.github.architers.syscenter.user.domain.dto.AddTenantUserDTO;
import io.github.architers.syscenter.user.domain.dto.AuthorizeRoleDTO;
import io.github.architers.syscenter.user.domain.dto.SysUserQueryDTO;
import io.github.architers.syscenter.user.domain.entity.SysUser;
import io.github.architers.syscenter.user.domain.vo.SysUserVO;
import io.github.architers.syscenter.user.service.SysUserService;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 系统用户api
 *
 * @author luyi
 */
@RestController
@RequestMapping("/userApi")
public class SysUserApi {

    @Resource
    private SysUserService sysUserService;

    /**
     * 分页查询系统用户
     */
    @GetMapping("/getUsersByPage")
    public PageResult<SysUserVO> getUsersByPage(@RequestBody @Validated PageRequest<SysUserQueryDTO> pageRequest) {
        return sysUserService.getUsersByPage(pageRequest);
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody @Validated AddTenantUserDTO add) {
        sysUserService.addSysUser(add);
    }

    /**
     * 编辑用户
     *
     * @param edit
     */
    @PutMapping("/editUser")
    public void editUser(@Validated SysUser edit) {
        sysUserService.editUser(edit);
    }

    /**
     * 授权角色
     */
    @PutMapping("/authorizeRole")
    public void authorizeRole(@RequestBody @Validated AuthorizeRoleDTO authorizeRoleDTO) {
        sysUserService.authorizeRole(authorizeRoleDTO);
    }

    /**
     * 改变用户状态
     */
    @PutMapping("/changUserStatus")
    public void changUserStatus(@RequestParam("userId") Long userId,
                                @RequestParam("status") Byte status) {
        sysUserService.changUserStatus(userId, status);
    }
}
