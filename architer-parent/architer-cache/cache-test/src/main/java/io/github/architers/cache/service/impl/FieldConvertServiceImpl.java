package io.github.architers.cache.service.impl;

import io.github.architers.cache.entity.FieldConvertCacheDTO;
import io.github.architers.cache.entity.FieldConvertNoCache;
import io.github.architers.cache.service.FieldConvertService;
import io.github.architers.context.cache.fieldconvert.IFieldConvert;
import io.github.architers.context.cache.fieldconvert.utils.FieldConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FieldConvertServiceImpl implements FieldConvertService, IFieldConvert<FieldConvertCacheDTO> {
    @Override
    public List<FieldConvertNoCache> convertNoCacheList(List<String> codes) {
        List<FieldConvertNoCache> list = findByCodes(codes);
        FieldConvertUtils.convert(list);
        return list;
    }

    public List<FieldConvertNoCache> findByCodes(List<String> codes) {
        List<FieldConvertNoCache> list = new ArrayList<>(codes.size());
        for (String code : codes) {
            FieldConvertNoCache fieldConvertNoCache = new FieldConvertNoCache();
            fieldConvertNoCache.setCode(code);
            list.add(fieldConvertNoCache);
        }
        return list;
    }


    @Override
    public Object getConvertValue(FieldConvertCacheDTO originData) {
        return originData.getCaption();
    }

    @Override
    public String getConverter() {
        return "fieldConverter";
    }

    @Override
    public FieldConvertCacheDTO getConvertOriginData(String code) {
        log.info("调用getConvertOriginData");
        FieldConvertCacheDTO fieldConvertInfo = new FieldConvertCacheDTO();
        fieldConvertInfo.setCode(code);
        fieldConvertInfo.setCaption(code + "_caption");
        return fieldConvertInfo;
    }

    @Override
    public Map<String, FieldConvertCacheDTO> getConvertOriginDatas(Collection<String> codes) {
        log.info("调用getConvertOriginDatas");
        List<FieldConvertCacheDTO> list = new ArrayList<>(codes.size());
        for (String code : codes) {
            FieldConvertCacheDTO fieldConvertInfo = new FieldConvertCacheDTO();
            fieldConvertInfo.setCode(code);
            fieldConvertInfo.setCaption(code + "_caption");
            list.add(fieldConvertInfo);
        }
        return list.stream().collect(Collectors.toMap(FieldConvertCacheDTO::getCode, e -> e));

    }


}
