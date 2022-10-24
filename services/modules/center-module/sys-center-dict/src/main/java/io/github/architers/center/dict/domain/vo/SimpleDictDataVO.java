package io.github.architers.center.dict.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 简单数据字典值
 *
 * @author luyi
 */
@Data
public class SimpleDictDataVO implements Serializable {

    /**
     * 字典值编码
     */
    private String dataCode;
    /**
     * 字典数据中文名称
     */
    private String dataCaption;
}
