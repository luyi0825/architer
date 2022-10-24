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
    private List<ImportJsonDictData> dictDataList;


    public static Dict convertToDict(ImportJsonDict importJsonDict) {
        Dict dict = new Dict();
        dict.setDictCode(importJsonDict.dictCode);
        dict.setDictCaption(importJsonDict.dictCaption);
        dict.setRemark(importJsonDict.remark);
        return dict;
    }


    public static ImportJsonDict convert2ImportJsonDict(Dict dict) {
        ImportJsonDict importJsonDict = new ImportJsonDict();
        importJsonDict.setDictCaption(dict.getDictCaption());
        importJsonDict.setDictCode(dict.getDictCode());
        importJsonDict.setRemark(dict.getRemark());
        return importJsonDict;
    }
}
