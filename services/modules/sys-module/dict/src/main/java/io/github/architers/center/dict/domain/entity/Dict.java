package io.github.architers.center.dict.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.architers.center.dict.domain.BaseTenantEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author luyi
 */
@TableName("sys_dict")
@Data
public class Dict extends BaseTenantEntity implements Serializable {

    /**
     * 数据字典主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    private String dictCode;
    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String dictCaption;
    /**
     * 备注
     */
    private String remark;

}
