package com.business.base.codemap.entity;


import com.architecture.context.common.valid.group.AddGroup;
import com.architecture.context.common.valid.group.UpdateGroup;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.business.base.codemap.constants.CodeMapValidConstant;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author luyi
 */
@Data
@TableName("t_base_code_map")
public class CodeMap implements Serializable {
    /**
     * 主键ID
     */
    @TableId
    @NotNull(message = CodeMapValidConstant.ID_NOT_NULL, groups = UpdateGroup.class)
    private Long id;
    /**
     * 编码
     */
    @NotBlank(message = CodeMapValidConstant.CODE_NOT_BLANK, groups = {AddGroup.class, UpdateGroup.class})
    @Length(min = 5, max = 30, message = CodeMapValidConstant.CODE_LENGTH_LIMIT, groups = {AddGroup.class, UpdateGroup.class})
    private String code;
    /**
     * 中文描述
     */
    @NotBlank(message = CodeMapValidConstant.CAPTION_NOT_BLANK, groups = {AddGroup.class, UpdateGroup.class})
    @Length(min = 1, max = 50, message = CodeMapValidConstant.CAPTION_LENGTH_LIMIT, groups = {AddGroup.class, UpdateGroup.class})
    private String caption;
    /**
     * 备注
     */
    @Length(max = 100, message = CodeMapValidConstant.REMARK_LENGTH_LIMIT, groups = {AddGroup.class, UpdateGroup.class})
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
