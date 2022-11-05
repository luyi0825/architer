package io.github.architers.center.dict.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 数据字典值查询的参数
 *
 * @author luyi
 */
@Data
public class DictDataQueryDTO implements Serializable {

    /**
     * 租户ID
     */
    private Integer tenantId;

    /**
     * 数据字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    private String dictCode;

    /**
     * 字典值编码
     */
    private String dataCode;
    /**
     * 字典数据中文名称
     */
    private String dataCaption;

    /**
     * 备注
     */
    private String remark;
}
