package io.github.architers.center.dict.service.impl;

import io.github.architers.center.dict.TenantUtils;
import io.github.architers.center.dict.domain.dto.ImportJsonDict;
import io.github.architers.center.dict.domain.entity.Dict;
import io.github.architers.center.dict.service.DictService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 数据字典对应的service
 *
 * @author luyi
 */
@Service
public class DictServiceImpl implements DictService {
    @Override
    public void importJsonDictData(List<ImportJsonDict> importJsonDictList) {
        Date current = new Date();
        List<Dict> dictList = new ArrayList<>(importJsonDictList.size());
        for (ImportJsonDict importJsonDict : importJsonDictList) {
            Dict dict = (Dict) importJsonDict.convertToDict().fillCreateOrUpdateField(current);
            dict.setTenantId(TenantUtils.getTenantId());
            dictList.add(dict);
        }
    }
}
