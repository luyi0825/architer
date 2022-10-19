package io.github.architers.center.dict.api;

import io.github.architers.center.dict.domain.dto.ImportJsonDict;
import io.github.architers.center.dict.service.DictService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典
 *
 * @author luyi
 */
@RestController
@RequestMapping("/dictApi")
public class DictApi {

    @Resource
    private DictService dictService;

    /**
     * 导入数据字典(json字符串)
     */
    @PostMapping("/importJsonDictData")
    public void importJsonDictData(@Validated @RequestBody List<ImportJsonDict> importJsonDictList) {
        dictService.importJsonDictData(null);
    }

    /**
     * 分页字典数据
     */
    public void getDictByPage() {

    }

    /**
     * 添加数据字典
     */
    public void addDict() {

    }

    /**
     * 编辑数据字典
     */
    public void editDict() {

    }

    /**
     * 删除数据字典
     */
    public void deleteDict() {

    }

    /**
     * 添加数据字典数据
     */
    public void addDictData() {

    }

    /**
     * 删除数据字典数据
     */
    public void deleteDictData() {

    }

    /**
     * 分页查询字询数据字典值
     */
    public void getDictDataByPage() {

    }

    /**
     * 通过字典编码查询数据字典值集合
     */
    public void getListByDictCode() {

    }


}
