package com.business.base.codemap.entity;

import com.architecture.ultimate.module.common.valid.group.AddGroup;
import com.architecture.ultimate.module.common.valid.group.UpdateGroup;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.business.base.codemap.constants.CodeMapItemValidConstant;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 代码集项
 *
 * @author luyi
 */
@Data
@TableName("t_base_code_map_item")
public class CodeMapItem implements Serializable {
    /**
     * 主键ID
     */
    @TableId
    @NotNull(message = CodeMapItemValidConstant.ID_NOT_NULL, groups = UpdateGroup.class)
    private Long id;
    /**
     * @see CodeMap#code
     */
    @NotBlank(message = CodeMapItemValidConstant.CODE_NOT_BLANK, groups = {AddGroup.class, UpdateGroup.class})
    private String code;
    /**
     * 代码集项编码
     */
    @NotBlank(message = CodeMapItemValidConstant.ITEM_CODE_NOT_BLANK, groups = {AddGroup.class, UpdateGroup.class})
    @Length(message = CodeMapItemValidConstant.ITEM_CODE_LENGTH_LIMIT, groups = {AddGroup.class, UpdateGroup.class}, max = 30)
    private String itemCode;
    /**
     * 代码集项中文名称
     */
    @NotBlank(message = CodeMapItemValidConstant.ITEM_CAPTION_NOT_BLANK, groups = {AddGroup.class, UpdateGroup.class})
    @Length(max = 50, message = CodeMapItemValidConstant.ITEM_CAPTION_LENGTH_LIMIT, groups = {AddGroup.class, UpdateGroup.class})
    private String itemCaption;
    /**
     * 备注
     */
    @Length(max = 100, message = CodeMapItemValidConstant.REMARK_LENGTH_LIMIT, groups = {AddGroup.class, UpdateGroup.class})
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

    @Override
    public String toString() {
        return "CodeMapItem{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", remark='" + remark + '\'' +
                ", itemCaption='" + itemCaption + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
