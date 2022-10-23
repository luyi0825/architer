package io.github.architers.center.menu.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.architers.common.module.tenant.domain.BaseEntity;
import io.github.architers.common.module.tenant.domain.BaseTenantEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author luyi
 */
@Data
public class SysTenant extends BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 租户名称
     */
    private String tenantCaption;
    /**
     * 租户编码
     */
    private String tenantCode;
    /**
     * token过期时间（分钟）
     */
    private Integer tokenExpireTime;
    /**
     * token最大有效时间（单位分钟：默认1天）
     */
    private Integer tokenMaxTime;
    /**
     * token是否自动刷新:0否，1是，默认1
     */
    private Boolean tokenAutoRefresh;

    /**
     * 状态：0禁用，1启动，默认0
     */
    private Byte status;

}
