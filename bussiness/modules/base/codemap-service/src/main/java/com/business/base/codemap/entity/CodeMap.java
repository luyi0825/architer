package com.business.base.codemap.entity;


import com.architecture.ultimate.module.common.valid.group.AddGroup;
import com.architecture.ultimate.module.common.valid.group.UpdateGroup;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author luyi
 */
@Data
@TableName("t_base_code_map")
public class CodeMap {
    /**
     * 主键ID
     */
    @TableId
    @NotNull(message = "主键ID不能为空", groups = UpdateGroup.class)
    private Long id;
    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Length(min = 5, max = 30, message = "编码长度在5~40之间", groups = {AddGroup.class, UpdateGroup.class})
    private String code;
    /**
     * 中文描述
     */
    @NotBlank(message = "中文描述不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Length(min = 1, max = 50, message = "中文描述长度不能超过50", groups = {AddGroup.class, UpdateGroup.class})
    private String caption;
    /**
     * 备注
     */
    @Length(max = 100, message = "中文描述长度不能超过100", groups = {AddGroup.class, UpdateGroup.class})
    private String remark;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 添加人
     */
    private String createUser;
    /**
     * 添加时间
     */
    private Date createTime;
}
