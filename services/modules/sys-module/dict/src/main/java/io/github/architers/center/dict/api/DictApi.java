package io.github.architers.center.dict.api;

import io.github.architers.center.dict.TenantUtils;
import io.github.architers.center.dict.domain.dto.ImportJsonDict;
import io.github.architers.center.dict.domain.entity.Dict;
import io.github.architers.center.dict.service.DictService;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        dictService.importJsonDictData(TenantUtils.getTenantId(), importJsonDictList);
    }

    /**
     * 导出数据字典json数据
     */
    @PostMapping("/exportJsonDictData")
    public void exportJsonDictData(HashSet<String> dictCodes) {
        dictService.exportJsonDictData(TenantUtils.getTenantId(), dictCodes);
    }

    /**
     * 分页字典数据
     */
    @PostMapping("/getDictByPage")
    public PageResult<Dict> getDictByPage(@Validated @RequestBody PageRequest<Dict> dictPageRequest) {
        return dictService.getDictByPage(dictPageRequest);
    }

    /**
     * 添加数据字典
     */
    @PostMapping("/addDict")
    public void addDict(@Validated @RequestBody Dict dict) {
        dictService.addDict(dict);
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
