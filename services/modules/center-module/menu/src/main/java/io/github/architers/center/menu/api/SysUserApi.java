package io.github.architers.center.menu.api;

import io.github.architers.center.menu.domain.dto.SysUserQueryDTO;
import io.github.architers.center.menu.domain.entity.SysUser;
import io.github.architers.center.menu.domain.vo.SysUserVO;
import io.github.architers.center.menu.service.SysUserService;
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
    public void addUser(@Validated SysUser sysUser) {
        sysUserService.addSysUser(sysUser);
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
     * 改变用户状态
     */
    @PutMapping("/changUserStatus")
    public void changUserStatus(@RequestParam("userId") Long userId,
                                @RequestParam("status") Byte status) {
        sysUserService.changUserStatus(userId, status);
    }
}
