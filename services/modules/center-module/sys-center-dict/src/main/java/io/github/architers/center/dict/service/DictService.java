package io.github.architers.center.dict.service;

import io.github.architers.center.dict.domain.dto.AddEditDictDTO;
import io.github.architers.center.dict.domain.dto.AddEditDictDataDTO;
import io.github.architers.center.dict.domain.dto.DictDataQueryDTO;
import io.github.architers.center.dict.domain.dto.ImportJsonDict;
import io.github.architers.center.dict.domain.entity.Dict;
import io.github.architers.center.dict.domain.entity.DictData;
import io.github.architers.center.dict.domain.vo.SimpleDictDataVO;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;

import java.util.List;
import java.util.Set;

/**
 * 数据字典对应的service
 *
 * @author luyi
 */
public interface DictService {
    void importJsonDictData(Integer tenantId, List<ImportJsonDict> importJsonDictList);

    void exportJsonDictData(Integer tenantId,Set<String> dictCodes);

    PageResult<Dict> getDictByPage(PageRequest<Dict> dictPageRequest);

    void addDict(AddEditDictDTO dict);

    void editDict(AddEditDictDTO edit);

    Dict getDictById(Long id);

    void deleteDictById(Long id);

    Long addDictData(AddEditDictDataDTO add);

    void editDictData(AddEditDictDataDTO edit);

    DictData findDictDataById(Long id);

    void deleteDictDataById(Long id);

    PageResult<DictData> getDictDataByPage(PageRequest<DictDataQueryDTO> dictPageRequest);

    List<SimpleDictDataVO> getSimpleListByDictCode(String dictCode);
}
