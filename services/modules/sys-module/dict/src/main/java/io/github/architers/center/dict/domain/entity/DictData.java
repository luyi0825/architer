package io.github.architers.center.dict.domain.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author luyi
 */
@Data
public class DictData {

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

}
