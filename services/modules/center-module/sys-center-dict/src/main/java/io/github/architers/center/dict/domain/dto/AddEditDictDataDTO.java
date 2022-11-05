package io.github.architers.center.dict.domain.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.architers.context.valid.ListValue;
import io.github.architers.context.valid.group.AddGroup;
import io.github.architers.context.valid.group.EditGroup;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 添加和编辑字典的参数信息
 *
 * @author luyi
 */
@Data
public class AddEditDictDataDTO implements Serializable {

    /**
     * 主键ID
     */
    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    @Null(message = "id参数有误", groups = AddGroup.class)
    private Long id;

    /**
     * 数据字典编码
     */
    @NotBlank(message = "字典编码不能为空", groups = AddGroup.class)
    private String dictCode;

    @NotBlank(message = "字典值编码不能为空", groups = AddGroup.class)
    private String dataCode;
    /**
     * 字典数据中文名称
     */
    @NotBlank(message = "字典值名称不能为空", groups = {AddGroup.class, EditGroup.class})
    private String dataCaption;

    /**
     * 备注
     */
    @Size(max = 50, message = "备注不能超过50字符", groups = {AddGroup.class, EditGroup.class})
    private String remark;

    /**
     * 状态
     */
    @ListValue(message = "状态值有误", value = {"0", "1"})
    private Byte status;

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
