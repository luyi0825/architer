package io.github.architers.center.dict.api;

import io.github.architers.center.dict.domain.dto.*;
import io.github.architers.common.module.tenant.TenantUtils;
import io.github.architers.center.dict.domain.entity.Dict;
import io.github.architers.center.dict.domain.entity.DictData;
import io.github.architers.center.dict.domain.vo.SimpleDictDataVO;
import io.github.architers.center.dict.service.DictService;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.context.valid.group.AddGroup;
import io.github.architers.context.valid.group.EditGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;
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
        dictService.importJsonDictData(TenantUtils.getTenantId(), importJsonDictList);
    }

    /**
     * 导出数据字典json数据
     */
    @PostMapping("/exportJsonDictData")
    public void exportJsonDictData(@RequestBody ExportDictDTO exportDictDTO) {
        dictService.exportJsonDictData(TenantUtils.getTenantId(), exportDictDTO);
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
    public void addDict(@RequestBody @Validated(AddGroup.class) AddEditDictDTO add) {
        dictService.addDict(add);
    }

    /**
     * 编辑数据字典
     */
    @PutMapping("/editDict")
    public void editDict(@RequestBody @Validated(EditGroup.class) AddEditDictDTO edit) {
        dictService.editDict(edit);
    }

    /**
     * 通过ID查询字典信息
     */
    @GetMapping("getDictById/{id}")
    public Dict getDictById(@PathVariable("id") Long id) {
        return dictService.getDictById(id);
    }

    /**
     * 删除数据字典
     */
    @DeleteMapping("/deleteDictById/{id}")
    public void deleteDictById(@PathVariable("id") Long id) {
        dictService.deleteDictById(id);
    }

    /**
     * 添加数据字典数据
     */
    @PostMapping("/addDictData")
    public Long addDictData(@RequestBody @Validated(AddGroup.class) AddEditDictDataDTO add) {
        return dictService.addDictData(add);
    }

    /**
     * 删除数据字典数据
     */
    @PutMapping("/editDictData")
    public void editDictData(@RequestBody @Validated(EditGroup.class) AddEditDictDataDTO edit) {
        dictService.editDictData(edit);
    }

    /**
     * 删除数据字典数据
     */
    @GetMapping("/findDictDataById/{id}")
    public DictData findDictDataById(@PathVariable("id") Long id) {
        return dictService.findDictDataById(id);
    }


    /**
     * 删除数据字典数据
     */
    @DeleteMapping("/deleteDictDataById/{id}")
    public void deleteDictDataById(@PathVariable("id") Long id) {
        dictService.deleteDictDataById(id);
    }

    /**
     * 分页查询字询数据字典值
     */
    @PostMapping("/getDictDataByPage")
    public PageResult<DictData> getDictDataByPage(@Validated @RequestBody PageRequest<DictDataQueryDTO> dictPageRequest) {
        return dictService.getDictDataByPage(dictPageRequest);
    }

    /**
     * 查询数据字典值集合(只有字典值编码和字典值中文)
     */
    @GetMapping("/getSimpleListByDictCode")
    public List<SimpleDictDataVO> getSimpleListByDictCode(@RequestParam("dictCode") String dictCode) {
        return dictService.getSimpleListByDictCode(dictCode);
    }

    @PutMapping("/changeDictDataStatus")
    public Boolean changeDictDataStatus(@RequestParam("dictDataId") long dictDataId,
                                        @RequestParam("status") Byte status) {
        return dictService.changeDictDataStatus(dictDataId, status);
    }


}
