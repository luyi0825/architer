package io.github.architers.center.dict.domain.dto;

import io.github.architers.center.dict.domain.entity.Dict;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 导入json 数据字典数据的DTO
 *
 * @author luyi
 */
@Data
public class ImportJsonDict {

    /**
     * 数据字典编码
     */
    @NotBlank(message = "数据字典编码不能为空")
    private String dictCode;

    /**
     * 数据字典名称
     */
    @NotBlank(message = "数据字典名称不能为空")
    private String dictCaption;
    /**
     * 备注
     */
    private String remark;

    /**
     * 数据字典值
     */
    @Valid
    private List<DictData> dictDataList;

    /**
     * 数据字典数据
     */
    @Data
    static class DictData {
        /**
         * 数据英文名称
         */
        @NotBlank(message = "字典值编码不能为空")
        private String dataCode;
        /**
         * 字典数据中文名称
         */
        @NotBlank(message = "字典值名称不能为空")
        private String dataCaption;

    }

    public Dict convertToDict() {
        Dict dict = new Dict();
        dict.setDictCode(this.dictCode);
        dict.setDictCaption(this.dictCaption);
        dict.setRemark(this.remark);
        return dict;
    }

}
