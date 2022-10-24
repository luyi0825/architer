package io.github.architers.syscenter.user.service;


import io.github.architers.syscenter.user.domain.dto.SysRoleQueryDTO;
import io.github.architers.syscenter.user.domain.entity.SysRole;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;

public interface SysRoleService {
    Integer addRole(SysRole add);

    PageResult<SysRole> getRolesByPage(PageRequest<SysRoleQueryDTO> pageRequest);

    void editRole(SysRole edit);
}
