package io.github.architers.server.file.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.excel.EasyExcel;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.exception.BusLogException;
import io.github.architers.context.model.TreeNode;
import io.github.architers.context.utils.JsonUtils;
import io.github.architers.context.utils.NodeTreeUtils;
import io.github.architers.objectstorage.ObjectStorage;
import io.github.architers.objectstorage.ObjectStorageType;
import io.github.architers.objectstorage.PutFileResponse;
import io.github.architers.server.file.model.entity.*;
import io.github.architers.server.file.model.param.FileTemplateAddParams;
import io.github.architers.server.file.eums.FileContentType;
import io.github.architers.server.file.model.param.FileTemplateCheckFileVersionParam;
import io.github.architers.server.file.model.param.FileTemplateCheckRowInfoParams;
import io.github.architers.server.file.mapper.ImportTemplateCatalogDao;
import io.github.architers.server.file.mapper.FileTemplateMapper;
import io.github.architers.server.file.model.dto.TemplateCatalogDTO;
import io.github.architers.server.file.model.dto.TemplateDTO;
import io.github.architers.server.file.eums.FileType;
import io.github.architers.server.file.service.ImportTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * 导入模板对应的service实现类
 *
 * @author luyi
 */
@Service
@Slf4j
public abstract class ImportTemplateServiceImpl extends ServiceImpl<FileTemplateMapper,FileTemplate> implements ImportTemplateService {

    @Resource
    private ImportTemplateCatalogDao importTemplateCatalogDao;

    @Resource
    private FileTemplateMapper fileTemplateMapper;

    @Resource(name = ObjectStorageType.OSS)
    private ObjectStorage objectStorage;

    @Override
    public void addTemplateCatalog(TemplateCatalogDTO templateCatalog) {
        TemplateCatalog importTemplateCatalog = new TemplateCatalog();
        BeanUtils.copyProperties(templateCatalog, importTemplateCatalog);
        importTemplateCatalog.setCreateTime(new Date());
        importTemplateCatalogDao.insert(importTemplateCatalog);
    }

    @Override
    public void editTemplateCatalog(TemplateCatalogDTO templateCatalog) {
        TemplateCatalog importTemplateCatalog = new TemplateCatalog();
        BeanUtils.copyProperties(templateCatalog, importTemplateCatalog);
        importTemplateCatalog.setCreateTime(new Date());
        importTemplateCatalogDao.updateById(importTemplateCatalog);
    }

    @Override
    public List<TreeNode> getTemplateCatalog() {
        List<TemplateCatalog> templateCatalogs = importTemplateCatalogDao.selectList(null);
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
    public void addTemplate(TemplateDTO templateDTO) {
        TemplateCatalog templateCatalog = importTemplateCatalogDao.selectById(templateDTO.getCatalogId());
        if (templateCatalog == null) {
            throw new BusLogException("模板目录不存在");
        }
        FileTemplate  template = new FileTemplate();
        BeanUtils.copyProperties(templateDTO, template);
        templateCatalog.setCreateTime(new Date());
        fileTemplateMapper.insert(template);
    }

    @Override
    public void editTemplate(TemplateDTO templateDTO) {
        FileTemplate template = new FileTemplate();
        BeanUtils.copyProperties(templateDTO, template);
        fileTemplateMapper.updateById(template);
    }


    /**
     * 支持模板
     */
    private static final List<FileType> SUPPORT_VERSION_TYPES = new ArrayList<>();

    static {
        SUPPORT_VERSION_TYPES.add(FileType.xlsx);
    }


    @Override
    public FileTemplate getFileTemplateByTemplateCode(String templateCode) {
        return null;
    }

//    /**
//     * @param file
//     * @param templateId
//     * @param refreshVersion
//     * @throws IOException
//     */
//    @Override
//    public void uploadTemplateFile(MultipartFile file, Integer templateId, boolean refreshVersion) throws IOException {
//        FileTemplate  template = fileTemplateMapper.selectById(templateId);
//        String key = "/importTemplate";
//        String contentType = file.getContentType();
//        FileType fileType = FileType.convertToContentTypeMap().get(contentType);
//        if (template.getSavePath().startsWith("/")) {
//            key =
//                    key + template.getSavePath();
//        } else {
//            key = key + "/" + template.getSavePath();
//        }
//        if (!template.getSavePath().endsWith("/")) {
//            key = key + "/";
//        }
//        key = key + template.getTemplateCode() + fileType.getSuffix();
//
//        PutFileResponse putFileResponse;
//        File tempFile = null;
//        try {
//            if (refreshVersion) {
//
//
//                if (!SUPPORT_VERSION_TYPES.contains(fileType)) {
//                    throw new BusLogException("该文件类型暂不支持刷新版本");
//                }
//                String newVersion = UUID.randomUUID().toString();
//                template.setVersion(newVersion);
//                tempFile = FileVersionUtils.fillFileVersion(file.getInputStream(), newVersion);
//                putFileResponse = objectStorage.putObject(tempFile, key);
//            } else {
//                putFileResponse = objectStorage.putObject(file.getInputStream(), key);
//            }
//            if (!putFileResponse.isResult()) {
//                throw new BusLogException("上传文件失败:" + putFileResponse.getErrorMessage());
//            }
//            template.setTemplateUrl(putFileResponse.getUrl());
//            fileTemplateMapper.updateById(template);
//        } finally {
//            FileUtils.deleteQuietly(tempFile);
//        }
//
//
//    }

    private PutFileResponse uploadTemplate(FileTemplate fileTemplate, File file) throws IOException {

        TemplateCatalog templateCatalog = importTemplateCatalogDao.selectById(fileTemplate.getCatalogId());
        if (templateCatalog == null) {
            throw new BusLogException("模板目录不存在");
        }
        Tika tika = new Tika();
        String type = tika.detect(file);
        String fileSuffix = FileContentType.getFileSuffixByContentType(type);
        String key = templateCatalog.getSavePath() + fileTemplate.getTemplateCode() + fileSuffix;
        PutFileResponse putFileResponse = objectStorage.putObject(file, key);
        if (putFileResponse.isResult()) {
            return putFileResponse;
        }
        throw new BusException("上传模板文件失败");

    }



    @Override
    public void addTemplateFile(FileTemplateAddParams addParams, MultipartFile file) throws IOException {

        TemplateCatalog templateCatalog = importTemplateCatalogDao.selectById(addParams.getCatalogId());
        if (templateCatalog == null) {
            throw new BusLogException("模板目录不存在");
        }
        File tempFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);


        FileTemplateCheckFileVersionParam checkFileVersionParam = addParams.getCheckFileVersion();
        FileTemplateCheckFileVersion checkFileVersion = new FileTemplateCheckFileVersion();
        if (checkFileVersionParam != null && Boolean.TRUE.equals(checkFileVersionParam.getEnableCheck())) {
            //TODO 填充版本
            Tika tika = new Tika();
            String type = tika.detect(tempFile);
            if (!FileContentType.xlsx.getContentType().equals(type)) {
                throw new BusException("该模板文件暂时不支持");
            }
            checkFileVersion.setEnableCheck(true);
            checkFileVersion.setFileVersion(UUID.randomUUID().toString());
        } else {
            checkFileVersion.setEnableCheck(false);
        }

        FileTemplateCheckRowInfo checkRowInfo = new FileTemplateCheckRowInfo();

        FileTemplateCheckRowInfoParams checkRowInfoParams = addParams.getCheckRowInfo();
        if (checkRowInfoParams != null && Boolean.TRUE.equals(checkRowInfoParams.getEnableCheck())) {
            Assert.notNull(checkRowInfoParams.getStartRow(), "开始行不能为空");
            Assert.notNull(checkRowInfoParams.getEndRow(), "结束行不能为空");
            if (checkRowInfoParams.getEndRow() < checkRowInfoParams.getStartRow()) {
                throw new BusException("结束行不能小于开始行");
            }
            EasyExcelCheckRowReadListener checkHeadReadListener = new EasyExcelCheckRowReadListener(checkRowInfoParams.getStartRow(), checkRowInfoParams.getEndRow());
            EasyExcel.read(tempFile, checkHeadReadListener).doReadAll();
            byte[] rowBytes = JsonUtils.toJsonBytes(checkHeadReadListener.getHeadDataList());
            String base64RowStr = org.apache.commons.codec.binary.Base64.encodeBase64String(rowBytes);
            checkRowInfo.setStartRow(checkRowInfoParams.getStartRow());
            checkRowInfo.setEndRow(checkRowInfoParams.getEndRow());
            checkRowInfo.setEnableCheck(true);
            checkRowInfo.setBase64RowStr(base64RowStr);
        } else {
            checkRowInfo.setEnableCheck(false);
        }


        FileTemplate fileTemplate = new FileTemplate();
        fileTemplate.setTemplateCode(addParams.getTemplateCode());
        fileTemplate.setCatalogId(addParams.getCatalogId());
        fileTemplate.setTemplateCaption(addParams.getTemplateCaption());
        fileTemplate.setRemark(addParams.getRemark());
        fileTemplate.setCheckFileVersion(checkFileVersion);
        fileTemplate.setCheckRowInfo(checkRowInfo);

        PutFileResponse putFileResponse = uploadTemplate(fileTemplate, tempFile);
        fileTemplate.setTemplateUrl(putFileResponse.getUrl());
        fileTemplate.setTemplateKey(putFileResponse.getKey());
    }



}
