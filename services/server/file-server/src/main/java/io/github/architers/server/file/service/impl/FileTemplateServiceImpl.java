package io.github.architers.server.file.service.impl;

import com.alibaba.excel.EasyExcel;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.exception.BusLogException;
import io.github.architers.context.model.TreeNode;
import io.github.architers.context.utils.JsonUtils;
import io.github.architers.context.utils.NodeTreeUtils;
import io.github.architers.context.utils.UserUtils;
import io.github.architers.objectstorage.ObjectStorage;
import io.github.architers.objectstorage.ObjectStorageType;
import io.github.architers.objectstorage.PutFileResponse;
import io.github.architers.server.file.domain.entity.*;
import io.github.architers.server.file.domain.param.FileTemplateParams;
import io.github.architers.server.file.eums.FileContentType;
import io.github.architers.server.file.domain.param.FileTemplateCheckRowInfoParams;
import io.github.architers.server.file.mapper.FileTemplateCatalogMapper;
import io.github.architers.server.file.mapper.FileTemplateMapper;
import io.github.architers.server.file.domain.param.TemplateCatalogParam;
import io.github.architers.server.file.service.FileTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;

/**
 * 导入模板对应的service实现类
 *
 * @author luyi
 */
@Service
@Slf4j
public class FileTemplateServiceImpl extends ServiceImpl<FileTemplateMapper, FileTemplate> implements FileTemplateService {

    @Resource
    private FileTemplateCatalogMapper fileTemplateCatalogMapper;

    @Resource
    private FileTemplateMapper fileTemplateMapper;

    @Resource(name = ObjectStorageType.OSS)
    private ObjectStorage objectStorage;

    @Override
    public void addTemplateCatalog(TemplateCatalogParam templateCatalog) {
        TemplateCatalog importTemplateCatalog = new TemplateCatalog();
        BeanUtils.copyProperties(templateCatalog, importTemplateCatalog);
        importTemplateCatalog.setCreateTime(new Date());
        fileTemplateCatalogMapper.insert(importTemplateCatalog);
    }

    @Override
    public void editTemplateCatalog(TemplateCatalogParam templateCatalog) {
        TemplateCatalog importTemplateCatalog = new TemplateCatalog();
        BeanUtils.copyProperties(templateCatalog, importTemplateCatalog);
        importTemplateCatalog.setCreateTime(new Date());
        fileTemplateCatalogMapper.updateById(importTemplateCatalog);
    }

    @Override
    public List<TreeNode> getTemplateCatalog() {
        List<TemplateCatalog> templateCatalogs = fileTemplateCatalogMapper.selectList(null);
        return NodeTreeUtils.convertToTree(templateCatalogs, "parentId", new Function<TemplateCatalog, TreeNode>() {
            @Override
            public TreeNode apply(TemplateCatalog templateCatalog) {
                TreeNode treeNode = new TreeNode();
                treeNode.setParentCode(String.valueOf(templateCatalog.getParentId()));
                treeNode.setCaption(templateCatalog.getCatalogCaption());
                treeNode.setCode(String.valueOf(templateCatalog.getId()));
                return treeNode;
            }
        });
    }

    @Override
    public String getNewTemplateFileVersion(String templateCode) {
        return null;
    }


    @Override
    public FileTemplate getFileTemplateByTemplateCode(String templateCode) {
        return baseMapper.selectByTemplateCode(templateCode);
    }


    private PutFileResponse uploadTemplate(FileTemplate fileTemplate, File file) throws IOException {

        TemplateCatalog templateCatalog = fileTemplateCatalogMapper.selectById(fileTemplate.getCatalogId());
        if (templateCatalog == null) {
            throw new BusLogException("模板目录不存在");
        }
        Tika tika = new Tika();
        String type = tika.detect(file);
        String fileSuffix = FileContentType.getFileSuffixByContentType(type);
        String key = templateCatalog.getSavePath() + "/" + fileTemplate.getTemplateCode() + fileSuffix;
        PutFileResponse putFileResponse = objectStorage.putObject(file, key);
        if (putFileResponse.isResult()) {
            return putFileResponse;
        }
        throw new BusException("上传模板文件失败");

    }


    @Override
    public void addTemplateFile(FileTemplateParams addParams) {

        TemplateCatalog templateCatalog = fileTemplateCatalogMapper.selectById(addParams.getCatalogId());
        if (templateCatalog == null) {
            throw new BusLogException("模板目录不存在");
        }
        FileTemplate existTemplate = this.getFileTemplateByTemplateCode(addParams.getTemplateCode());
        if (existTemplate != null) {
            throw new BusException("该模板编码已经存在");
        }
        FileTemplateCheckRowInfo checkRowInfo = new FileTemplateCheckRowInfo();

        FileTemplateCheckRowInfoParams checkRowInfoParams = addParams.getCheckRowInfo();
        //校验数据合法性
        checkRowInfoParams.checkDataLegality();
        //校验行数据赋值
        checkRowInfo.setStartRow(checkRowInfoParams.getStartRow());
        checkRowInfo.setEndRow(checkRowInfoParams.getEndRow());
        checkRowInfo.setEnableCheck(checkRowInfoParams.getEnableCheck());

        FileTemplate fileTemplate = new FileTemplate();
        fileTemplate.setTemplateCode(addParams.getTemplateCode());
        fileTemplate.setCatalogId(addParams.getCatalogId());
        fileTemplate.setTemplateCaption(addParams.getTemplateCaption());
        fileTemplate.setRemark(addParams.getRemark());
        fileTemplate.setCheckRowInfo(checkRowInfo);
        fileTemplate.setUpdateBy(UserUtils.getUserId());
        fileTemplate.setCreateBy(UserUtils.getUserId());

        try {
            this.save(fileTemplate);
        } catch (DuplicateKeyException e) {
            log.info("模板编码[{}]已经存在", fileTemplate.getTemplateCode());
            throw new BusException("模板编码已经存在");
        }
    }

    @Override
    public void editFileTemplate(FileTemplateParams editParam) throws Exception {
        TemplateCatalog templateCatalog = fileTemplateCatalogMapper.selectById(editParam.getCatalogId());
        if (templateCatalog == null) {
            throw new BusLogException("模板目录不存在");
        }
        FileTemplate fileTemplate = fileTemplateMapper.selectByTemplateCode(editParam.getTemplateCode());
        fileTemplate.setTemplateCaption(editParam.getTemplateCaption());
        fileTemplate.setRemark(editParam.getRemark());
        fileTemplate.setUpdateBy(UserUtils.getUserId());

        {//校验行信息
            FileTemplateCheckRowInfo checkRowInfo = fileTemplate.getCheckRowInfo();
            FileTemplateCheckRowInfoParams checkRowInfoParams = editParam.getCheckRowInfo();
            checkRowInfoParams.checkDataLegality();
            String base64RowStr = null;
            if (checkRowInfoParams.needRefreshBase64RowStr(checkRowInfo)) {
                if (StringUtils.hasText(fileTemplate.getTemplateKey())) {
                    InputStream inputStream = objectStorage.getObjectInputStream(fileTemplate.getTemplateKey());

                    EasyExcelCheckRowReadListener checkHeadReadListener = new EasyExcelCheckRowReadListener(checkRowInfoParams.getStartRow(), checkRowInfoParams.getEndRow());
                    EasyExcel.read(inputStream, checkHeadReadListener).doReadAll();
                    byte[] rowBytes = JsonUtils.toJsonBytes(checkHeadReadListener.getHeadDataList());
                    base64RowStr = org.apache.commons.codec.binary.Base64.encodeBase64String(rowBytes);
                }
            }
            checkRowInfo.setStartRow(checkRowInfoParams.getStartRow());
            checkRowInfo.setEndRow(checkRowInfoParams.getEndRow());
            checkRowInfo.setEnableCheck(checkRowInfoParams.getEnableCheck());
            checkRowInfo.setBase64RowStr(base64RowStr);
        }
        this.updateById(fileTemplate);
    }

    @Override
    public void uploadTemplateFile(MultipartFile file, Integer templateId) throws IOException {
        File tempFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            FileTemplate fileTemplate = this.getById(templateId);
            FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);


            FileTemplateCheckRowInfo checkRowInfo = fileTemplate.getCheckRowInfo();
            EasyExcelCheckRowReadListener checkHeadReadListener = new EasyExcelCheckRowReadListener(checkRowInfo.getStartRow(), checkRowInfo.getEndRow());
            EasyExcel.read(tempFile, checkHeadReadListener).doReadAll();
            byte[] rowBytes = JsonUtils.toJsonBytes(checkHeadReadListener.getHeadDataList());
            String base64RowStr = org.apache.commons.codec.binary.Base64.encodeBase64String(rowBytes);

            PutFileResponse putFileResponse = uploadTemplate(fileTemplate, tempFile);

            fileTemplate.setTemplateUrl(putFileResponse.getUrl());
            fileTemplate.setTemplateKey(putFileResponse.getKey());
            fileTemplate.setUpdateBy(UserUtils.getUserId());
            fileTemplate.getCheckRowInfo().setBase64RowStr(base64RowStr);
            this.updateById(fileTemplate);
        } finally {
            FileUtils.deleteQuietly(tempFile);
        }
    }
}
