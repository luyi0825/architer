package io.github.architers.syscenter.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 系统用户角色
 *
 * @author luyi
 */
@Data
public class SysUserRole  {
    /**
     * 用户角色中间表ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
