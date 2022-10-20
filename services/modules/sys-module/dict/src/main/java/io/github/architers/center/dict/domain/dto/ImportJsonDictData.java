package io.github.architers.center.dict.domain.dto;

import io.github.architers.center.dict.domain.entity.DictData;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ImportJsonDictData {
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

    public static DictData convert2DictData(ImportJsonDictData importJsonDictData) {
        DictData dictData = new DictData();
        dictData.setDataCaption(importJsonDictData.getDataCaption());
        dictData.setDataCode(importJsonDictData.getDataCode());
        return dictData;
    }

    public static ImportJsonDictData convert2ImportDictData(DictData dictData) {
        ImportJsonDictData importJsonDictData = new ImportJsonDictData();
        importJsonDictData.setDataCode(dictData.getDataCode());
        importJsonDictData.setDataCaption(dictData.getDataCaption());
        return importJsonDictData;
    }


}
