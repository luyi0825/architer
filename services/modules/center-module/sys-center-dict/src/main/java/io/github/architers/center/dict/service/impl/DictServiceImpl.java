package io.github.architers.center.dict.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.architers.center.dict.domain.dto.*;
import io.github.architers.center.dict.domain.vo.SimpleDictDataVO;
import io.github.architers.component.mybatisplus.MybatisPageUtils;
import io.github.architers.common.module.tenant.TenantUtils;
import io.github.architers.center.dict.domain.entity.Dict;
import io.github.architers.center.dict.domain.entity.DictData;
import io.github.architers.center.dict.dao.DictDataDao;
import io.github.architers.center.dict.dao.DictDao;
import io.github.architers.center.dict.service.DictService;
import io.github.architers.context.exception.NoStackBusException;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.context.sql.SqlTaskUtils;
import io.github.architers.context.utils.JsonUtils;
import io.github.architers.context.web.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
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
    private DictDao dictDao;

    @Resource
    private DictDataDao dictDataDao;

    @Override
    public void importJsonDictData(Integer tenantId, List<ImportJsonDict> importJsonDictList) {
        Date current = new Date();
        List<Dict> dictList = new ArrayList<>(importJsonDictList.size());
        List<DictData> dictDataList = new LinkedList<>();
        for (ImportJsonDict importJsonDict : importJsonDictList) {
            Dict dict = ImportJsonDict.convertToDict(importJsonDict);
            dict.setTenantId(TenantUtils.getTenantId());
            dict.fillCreateAndUpdateField(current);
            dictList.add(dict);
            //数据字典值
            if (importJsonDict.getDictDataList() != null) {
                for (ImportJsonDictData importJsonDictData : importJsonDict.getDictDataList()) {
                    DictData dictData = ImportJsonDictData.convert2DictData(importJsonDictData);
                    dictData.fillCreateAndUpdateField(current);
                    dictData.setDictCode(dict.getDictCode());
                    dictData.setTenantId(TenantUtils.getTenantId());
                    dictDataList.add(dictData);
                }
            }
        }
        //删除之前的
        Set<String> dictCodes = dictList.stream().map(Dict::getDictCode).collect(Collectors.toSet());
        //执行sql任务
        SqlTaskUtils.executor(() -> {
            dictDao.deleteByDictCode(tenantId, dictCodes);
            //查询已有的
            dictDao.insertBatch(dictList);
            dictDataDao.findByDictCodes(tenantId, dictCodes);
            dictDataDao.insertBatch(dictDataList);
        });

    }

    @Override
    public void exportJsonDictData(Integer tenantId, ExportDictDTO exportDictDTO) {
        exportDictDTO.setTenantId(TenantUtils.getTenantId());
        //查询数据字典
        List<Dict> dictList = dictDao.selectForExportDict(exportDictDTO);

        if (CollectionUtils.isEmpty(dictList)) {
            throw new NoStackBusException("没有数据字典需要导出");
        }
        //查询数据字典值
        Set<String> dictCodes =
                dictList.stream().map(Dict::getDictCode).collect(Collectors.toSet());
        List<DictData> dictDataList = dictDataDao.findByDictCodes(tenantId, dictCodes);
        Map<String, List<DictData>> dictDataMap =
                dictDataList.stream().collect(Collectors.groupingBy(DictData::getDictCode));
        //转换为ImportJsonDict
        List<ImportJsonDict> importJsonDictList = new ArrayList<>(dictList.size());
        for (Dict dict : dictList) {
            ImportJsonDict importJsonDict = ImportJsonDict.convert2ImportJsonDict(dict);
            List<DictData> dictDatas = dictDataMap.get(dict.getDictCode());
            importJsonDictList.add(importJsonDict);

            if (dictDatas == null) {
                continue;
            }
            List<ImportJsonDictData> importJsonDictDataList = new ArrayList<>(dictDatas.size());
            for (DictData dictData : dictDatas) {
                ImportJsonDictData importJsonDictData = ImportJsonDictData.convert2ImportDictData(dictData);
                importJsonDictDataList.add(importJsonDictData);
            }
            importJsonDict.setDictDataList(importJsonDictDataList);
        }
        //转为json对象
        HttpServletResponse response = ServletUtils.response();
        try (OutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
            String fileName = UUID.randomUUID() + ".txt";
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            response.setContentType("application/octet-stream");
            outputStream.write(JsonUtils.writeValueAsBytes(importJsonDictList));
            outputStream.flush();
        } catch (IOException ex) {
            log.error("download file error!", ex);
            throw new RuntimeException("导出代码集失败");
        }
    }

    @Override
    public PageResult<Dict> getDictByPage(PageRequest<Dict> pageRequest) {
        return MybatisPageUtils.pageQuery(pageRequest.getPageParam(),
                () -> {
                    Dict dict = pageRequest.getRequestParam();
                    Wrapper<Dict> dictWrapper =
                            Wrappers.lambdaQuery(Dict.class)
                                    .likeRight(StringUtils.hasText(dict.getDictCode()), Dict::getDictCode, dict.getDictCode())
                                    .likeRight(StringUtils.hasText(dict.getDictCaption()), Dict::getDictCaption, dict.getDictCaption());
                    return dictDao.selectList(dictWrapper);
                });
    }

    @Override
    public void addDict(AddEditDictDTO addParam) {
        //TODO 判断数据是否重复
        int count = dictDao.countByDictCode(TenantUtils.getTenantId(), addParam.getDictCode());
        if (count > 0) {
            throw new NoStackBusException("数据字典已经存在");
        }
        Dict dict = new Dict();
        BeanUtils.copyProperties(addParam, dict);
        dict.fillCreateAndUpdateField(null);
        dict.setTenantId(TenantUtils.getTenantId());
        dictDao.insert(dict);
    }

    @Override
    public void editDict(AddEditDictDTO edit) {
        Dict dict = new Dict();
        //字典编码不能编辑
        edit.setDictCode(null);
        BeanUtils.copyProperties(edit, dict);
        dict.fillCreateAndUpdateField(null);
        int count = dictDao.updateById(dict);
        if (count != 1) {
            throw new BusException("编辑字典失败");
        }
    }

    @Override
    public Dict getDictById(Long id) {
        return dictDao.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictById(Long id) {
        Dict dict = dictDao.selectById(id);
        if (dict == null) {
            throw new BusException("数据字典已经被删除或者不存在");
        }
        List<DictData> dictDataList = dictDataDao.findByDictCodes(TenantUtils.getTenantId(),
                Collections.singleton(dict.getDictCode()));
        if (!CollectionUtils.isEmpty(dictDataList)) {
            throw new BusException("存在字典值，无法删除！");
        }
        dictDao.deleteById(id);

    }

    @Override
    public Long addDictData(AddEditDictDataDTO add) {
        //判断数据字典是否存在
        int count = dictDao.countByDictCode(TenantUtils.getTenantId(), add.getDictCode());
        if (count == 0) {
            throw new BusException("数据字典不存在");
        }
        if (count > 1) {
            throw new BusException("数据字典重复");
        }
        //判断字典值编码是否重复
        count = dictDataDao.countByDictCodeAndDataCode(TenantUtils.getTenantId(), add.getDictCode(), add.getDataCode());
        if (count > 0) {
            if (count > 1) {
                log.error("字典值编码【{}】重复", add.getDataCode());
            }
            throw new BusException("字典值编码【" + add.getDataCode() + "】已经存在");
        }

        DictData dictData = new DictData();
        BeanUtils.copyProperties(add, dictData);
        dictData.setTenantId(TenantUtils.getTenantId());
        dictData.fillCreateAndUpdateField(new Date());
        dictDataDao.insert(dictData);
        return dictData.getId();
    }

    @Override
    public void editDictData(AddEditDictDataDTO edit) {
        DictData dictData = new DictData();
        BeanUtils.copyProperties(edit, dictData);
        //字典编码、字典值编码、租户不能编辑
        edit.setDataCode(null);
        edit.setDataCode(null);
        dictData.setTenantId(TenantUtils.getTenantId());
        dictData.fillCreateAndUpdateField(new Date());
        dictDataDao.updateById(dictData);
    }

    @Override
    public DictData findDictDataById(Long id) {
        return dictDataDao.selectById(id);
    }

    @Override
    public void deleteDictDataById(Long id) {
        int count = dictDataDao.deleteById(id);
        if (count != 1) {
            throw new BusException("删除字典值数据失败");
        }
    }

    @Override
    public PageResult<DictData> getDictDataByPage(PageRequest<DictDataQueryDTO> dictPageRequest) {
        DictDataQueryDTO dictDataQueryDTO = dictPageRequest.getRequestParam();
        Wrapper<DictData> wrapper = Wrappers.lambdaQuery(DictData.class)
                .eq(DictData::getTenantId, TenantUtils.getTenantId())
                .eq(DictData::getDictCode, dictDataQueryDTO.getDictCode())
                .like(StringUtils.hasText(dictDataQueryDTO.getDataCode()), DictData::getDataCode, dictDataQueryDTO.getDataCode())
                .like(StringUtils.hasText(dictDataQueryDTO.getDataCaption()), DictData::getDataCaption, dictDataQueryDTO.getDataCaption())
                .orderByAsc(DictData::getDataCode);
        return MybatisPageUtils.pageQuery(dictPageRequest.getPageParam(),
                () -> dictDataDao.selectList(wrapper));
    }

    @Override
    public List<SimpleDictDataVO> getSimpleListByDictCode(String dictCode) {
        return dictDataDao.getSimpleListByDictCode(TenantUtils.getTenantId(), dictCode);
    }

    @Override
    public Boolean changeDictDataStatus(long dictDataId, Byte status) {
        DictData dictData = new DictData();
        dictData.setId(dictDataId);
        dictData.setStatus(status);
        dictData.fillCreateAndUpdateField(new Date());
        return dictDataDao.updateById(dictData) == 1;
    }


}
