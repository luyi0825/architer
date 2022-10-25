package io.github.architers.syscenter.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.architers.common.module.tenant.domain.BaseTenantEntity;
import lombok.Data;

/**
 * 租户用户
 *
 * @author luyi
 */
@Data
public class SysTenantUser extends BaseTenantEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 状态:0禁用，1启用
     */
    private Byte status;

}
