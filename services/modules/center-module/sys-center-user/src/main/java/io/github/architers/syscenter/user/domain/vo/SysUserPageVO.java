package io.github.architers.syscenter.user.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.architers.common.module.tenant.domain.BaseTenantEntity;
import lombok.Data;

/**
 * 用户分页VO
 */
@Data
public class SysUserPageVO extends BaseTenantEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户中文名称
     */
    private String userCaption;

    /**
     * 状态:0禁用，1启用
     */
    private Byte status;
}
