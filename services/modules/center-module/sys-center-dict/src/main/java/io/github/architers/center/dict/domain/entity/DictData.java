package io.github.architers.center.dict.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.architers.common.module.tenant.domain.BaseTenantEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author luyi
 */
@Data
@TableName("sys_dict_data")
public class DictData extends BaseTenantEntity implements Serializable {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 数据字典编码
     */
    private String dictCode;

    @NotBlank(message = "字典值编码不能为空")
    private String dataCode;
    /**
     * 字典数据中文名称
     */
    @NotBlank(message = "字典值名称不能为空")
    private String dataCaption;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 备注
     */
    private String remark;


}
