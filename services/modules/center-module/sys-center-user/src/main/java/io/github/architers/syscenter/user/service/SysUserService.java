package io.github.architers.syscenter.user.service;

import io.github.architers.syscenter.user.domain.dto.AddTenantUserDTO;
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

    SysUser selectByUserName(String userName);
}
