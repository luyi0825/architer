package io.github.architers.syscenter.user.service;

import io.github.architers.syscenter.user.domain.dto.AddTenantUserDTO;
import io.github.architers.syscenter.user.domain.dto.AuthorizeRoleDTO;
import io.github.architers.syscenter.user.domain.dto.SysUserQueryDTO;
import io.github.architers.syscenter.user.domain.entity.SysUser;
import io.github.architers.syscenter.user.domain.vo.SysUserVO;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;

/**
 * @author luyi
 */
public interface SysUserService {
    /**
     * 分页查询系统用户
     */
    PageResult<SysUserVO> getUsersByPage(PageRequest<SysUserQueryDTO> pageRequest);

    void addSysUser(AddTenantUserDTO sysUser);

    void editUser(SysUser edit);


    void changUserStatus(Long userId, Byte status);

    /**
     * 通过用户名查询用户用户信息
     *
     * @param userName 系统用户名
     * @return 用户信息
     */
    SysUser selectByUserName(String userName);

    /**
     * 授权角色
     *
     * @param authorizeRoleDTO 用户授权角色参数
     */
    void authorizeRole(AuthorizeRoleDTO authorizeRoleDTO);
}
