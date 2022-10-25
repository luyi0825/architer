package io.github.architers.syscenter.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.architers.component.mybatisplus.InsertBatch;
import io.github.architers.syscenter.user.domain.entity.SysUserRole;

/**
 * @author luyi
 */
public interface SysUserRoleDao extends  BaseMapper<SysUserRole>, InsertBatch<SysUserRole> {
}
