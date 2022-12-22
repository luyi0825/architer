package io.github.architers.server.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.utils.NodeTreeUtils;
import io.github.architers.objectstorage.ObjectStorage;
import io.github.architers.objectstorage.ObjectStorageType;
import io.github.architers.objectstorage.PutFileResponse;
import io.github.architers.server.file.utils.FileVersionUtils;
import io.github.architers.server.file.dao.ImportTemplateCatalogDao;
import io.github.architers.server.file.dao.ImportTemplateDao;
import io.github.architers.server.file.domain.dto.TemplateCatalogDTO;
import io.github.architers.server.file.domain.dto.TemplateDTO;
import io.github.architers.server.file.domain.entity.Template;
import io.github.architers.server.file.domain.entity.TemplateCatalog;
import io.github.architers.server.file.eums.FileType;
import io.github.architers.server.file.service.ImportTemplateService;
import org.apache.commons.io.FileUtils;
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
public class ImportTemplateServiceImpl implements ImportTemplateService {

    @Resource
    private ImportTemplateCatalogDao importTemplateCatalogDao;

    @Resource
    private ImportTemplateDao importTemplateDao;

    @Resource(name = ObjectStorageType.MINIO)
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
    public List<NodeTreeUtils.TreeNode> getTemplateCatalog() {
        List<TemplateCatalog> templateCatalogs = importTemplateCatalogDao.selectList(null);
        return NodeTreeUtils.convertToTree(templateCatalogs, "parentId", new Function<TemplateCatalog, NodeTreeUtils.TreeNode>() {
            @Override
            public NodeTreeUtils.TreeNode apply(TemplateCatalog templateCatalog) {
                NodeTreeUtils.TreeNode treeNode = new NodeTreeUtils.TreeNode();
                treeNode.setParentCode(templateCatalog.getParentId() + "");
                treeNode.setCaption(templateCatalog.getCatalogCaption());
                treeNode.setCode(templateCatalog.getId() + "");
                return treeNode;
            }
        });
    }

    @Override
    public void addTemplate(TemplateDTO templateDTO) {
        TemplateCatalog templateCatalog = importTemplateCatalogDao.selectById(templateDTO.getCatalogId());
        if (templateCatalog == null) {
            throw new BusException("模板目录不存在");
        }
        Template template = new Template();
        BeanUtils.copyProperties(templateDTO, template);
        templateCatalog.setCreateTime(new Date());
        importTemplateDao.insert(template);
    }

    @Override
    public void editTemplate(TemplateDTO templateDTO) {
        Template template = new Template();
        BeanUtils.copyProperties(templateDTO, template);
        importTemplateDao.updateById(template);
    }


    /**
     * 支持模板
     */
    private static final List<FileType> SUPPORT_VERSION_TYPES = new ArrayList<>();

    static {
        SUPPORT_VERSION_TYPES.add(FileType.xlsx);
    }

    /**
     * @param file
     * @param templateId
     * @param refreshVersion
     * @throws IOException
     */
    @Override
    public void uploadTemplateFile(MultipartFile file, Integer templateId, boolean refreshVersion) throws IOException {
        Template template = importTemplateDao.selectById(templateId);
        String key = "/importTemplate";
        String contentType = file.getContentType();
        FileType fileType = FileType.convertToContentTypeMap().get(contentType);
        if (template.getSavePath().startsWith("/")) {
            key =
                    key + template.getSavePath();
        } else {
            key = key + "/" + template.getSavePath();
        }
        if (!template.getSavePath().endsWith("/")) {
            key = key + "/";
        }
        key = key + template.getTemplateCode() + fileType.getSuffix();

        PutFileResponse putFileResponse;
        File tempFile = null;
        try {
            if (refreshVersion) {


                if (!SUPPORT_VERSION_TYPES.contains(fileType)) {
                    throw new BusException("该文件类型暂不支持刷新版本");
                }
                String newVersion = UUID.randomUUID().toString();
                template.setVersion(newVersion);
                tempFile = FileVersionUtils.fillFileVersion(file.getInputStream(), newVersion);
                putFileResponse = objectStorage.putObject(tempFile, key);
            } else {
                putFileResponse = objectStorage.putObject(file.getInputStream(), key);
            }
            if (!putFileResponse.isResult()) {
                throw new BusException("上传文件失败:" + putFileResponse.getErrorMessage());
            }
            template.setTemplateUrl(putFileResponse.getUrl());
            importTemplateDao.updateById(template);
        } finally {
            FileUtils.deleteQuietly(tempFile);
        }


    }

    @Override
    public String getNewTemplateFileVersion(String templateCode) {
        Wrapper<Template> templateWrapper = Wrappers.lambdaQuery(Template.class)
                .select(Template::getTemplateCode, Template::getVersion)
                .eq(Template::getTemplateCode, templateCode);
        Template template = importTemplateDao.selectOne(templateWrapper);
        if (template == null) {
            throw new BusException("模板文件不存在");
        }
        return template.getVersion();
    }
}
