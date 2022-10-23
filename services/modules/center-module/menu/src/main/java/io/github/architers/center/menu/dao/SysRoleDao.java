package io.github.architers.center.menu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.architers.center.menu.domain.entity.SysRole;
import org.apache.ibatis.annotations.Param;

public interface SysRoleDao extends BaseMapper<SysRole> {
    int countByRoleName(@Param("tenantId") Integer tenantId,
                        @Param("roleName") String roleName);
}
