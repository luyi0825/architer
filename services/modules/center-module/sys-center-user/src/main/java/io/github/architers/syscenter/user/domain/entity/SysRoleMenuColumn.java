package io.github.architers.syscenter.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.architers.common.module.tenant.domain.BaseEntity;
import lombok.Data;

@Data
public class SysRoleMenuColumn extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 列表对应列的ID
     */
    private Long listColumnId;


}
