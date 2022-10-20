package io.github.architers.center.dict.service.impl;

import com.github.pagehelper.PageHelper;
import io.github.architers.center.dict.PageUtils;
import io.github.architers.center.dict.TenantUtils;
import io.github.architers.center.dict.domain.dto.ImportJsonDictData;
import io.github.architers.center.dict.domain.dto.ImportJsonDict;
import io.github.architers.center.dict.domain.entity.Dict;
import io.github.architers.center.dict.domain.entity.DictData;
import io.github.architers.center.dict.mapper.DictDataMapper;
import io.github.architers.center.dict.mapper.DictMapper;
import io.github.architers.center.dict.service.DictService;
import io.github.architers.context.query.PageParam;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.context.utils.JsonUtils;
import io.github.architers.context.web.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 数据字典对应的service
 *
 * @author luyi
 */
@Service
@Slf4j
public class DictServiceImpl implements DictService {


    @Resource
    private DictMapper dictMapper;

    @Resource
    private DictDataMapper dictDataMapper;

    @Override
    public void importJsonDictData(Integer tenantId, List<ImportJsonDict> importJsonDictList) {
        Date current = new Date();
        List<Dict> dictList = new ArrayList<>(importJsonDictList.size());
        List<DictData> dictDataList = new LinkedList<>();
        for (ImportJsonDict importJsonDict : importJsonDictList) {
            Dict dict = (Dict) ImportJsonDict.convertToDict(importJsonDict).fillCreateOrUpdateField(current);
            dict.setTenantId(TenantUtils.getTenantId());
            dictList.add(dict);
            //数据字典值
            if (importJsonDict.getDictDataList() != null) {
                for (ImportJsonDictData importJsonDictData : importJsonDict.getDictDataList()) {
                    DictData dictData = ImportJsonDictData.convert2DictData(importJsonDictData);
                    dictDataList.add(dictData);
                }
            }
        }
        //删除之前的
        Set<String> dictCodes = dictList.stream().map(Dict::getDictCode).collect(Collectors.toSet());
        dictMapper.deleteByDictCode(tenantId, dictCodes);
        //查询已有的
        dictMapper.insertBatchSomeColumn(dictList);
        dictDataMapper.insertBatchSomeColumn(dictDataList);
    }

    @Override
    public void exportJsonDictData(Integer tenantId, Set<String> dictCodes) {
        //查询数据字典
        List<Dict> dictList = dictMapper.findByDictCodes(tenantId, dictCodes);
        if (CollectionUtils.isEmpty(dictList)) {
            throw new RuntimeException("没有数据需要导出");
        }
        //查询数据字典值
        List<DictData> dictDataList = dictDataMapper.findByDictCodes(tenantId, dictCodes);
        Map<String, List<DictData>> dictDataMap = dictDataList.stream().collect(Collectors.groupingBy(DictData::getDataCode));
        //转换为ImportJsonDict
        List<ImportJsonDict> importJsonDictList = new ArrayList<>(dictList.size());
        for (Dict dict : dictList) {
            ImportJsonDict importJsonDict = ImportJsonDict.convert2ImportJsonDict(dict);
            List<DictData> dictDatas = dictDataMap.get(dict.getDictCode());
            List<ImportJsonDictData> importJsonDictDataList = new ArrayList<>(dictDatas.size());
            for (DictData dictData : dictDatas) {
                ImportJsonDictData importJsonDictData = ImportJsonDictData.convert2ImportDictData(dictData);
                importJsonDictDataList.add(importJsonDictData);
            }
            importJsonDictList.add(importJsonDict);
            importJsonDict.setDictDataList(importJsonDictDataList);
        }
        //转为json对象
        HttpServletResponse response = ServletUtils.response();
        try (OutputStream toClient = new BufferedOutputStream(response.getOutputStream())) {
            String fileName = UUID.randomUUID() + ".txt";

            response.reset();
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

            response.setContentType("application/octet-stream");
            toClient.write(JsonUtils.writeValueAsBytes(importJsonDictList));
            toClient.flush();
        } catch (IOException ex) {
            log.error("download file error!", ex);
            throw new RuntimeException("导出代码集失败");
        }
    }

    @Override
    public PageResult<Dict> getDictByPage(PageRequest<Dict> pageRequest) {
        return PageUtils.pageQuery(pageRequest.getPageParam(), () -> dictMapper.selectList(null));
    }


}
