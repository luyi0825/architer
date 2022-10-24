package io.github.architers.center.dict.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.architers.context.valid.group.AddGroup;
import io.github.architers.context.valid.group.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 添加和编辑字典的参数信息
 *
 * @author luyi
 */
@Data
public class AddEditDictDTO implements Serializable {

    /**
     * 数据字典主键ID
     */
    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    @Null(message = "id参数有误", groups = AddGroup.class)
    private Long id;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空", groups = {AddGroup.class, EditGroup.class})
    private String dictCode;
    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String dictCaption;
    /**
     * 备注
     */
    @Size(max = 50, message = "备注不能超过50字符", groups = {AddGroup.class, EditGroup.class})
    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createTime;

    /**
     * 创建人
     */
    protected Long createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date updateTime;

    /**
     * 创建人
     */
    protected Long updateBy;
}
