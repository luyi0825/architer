package io.github.architers.center.dict.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.architers.center.dict.domain.BaseTenantEntity;
import io.github.architers.context.valid.group.AddGroup;
import io.github.architers.context.valid.group.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author luyi
 */
@Data
@TableName("sys_dict")
public class Dict extends BaseTenantEntity implements Serializable {

    /**
     * 数据字典主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 字典名称
     */
    private String dictCaption;
    /**
     * 备注
     */
    private String remark;

}
