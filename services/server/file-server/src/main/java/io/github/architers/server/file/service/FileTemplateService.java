package io.github.architers.server.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.architers.context.model.TreeNode;
import io.github.architers.server.file.domain.param.TemplateCatalogParam;
import io.github.architers.server.file.domain.entity.FileTemplate;
import io.github.architers.server.file.domain.param.FileTemplateParams;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileTemplateService extends IService<FileTemplate> {
    /**
     * 添加模板目录
     *
     * @param templateCatalog 需要添加的模板目录信息
     */
    void addTemplateCatalog(TemplateCatalogParam templateCatalog);

    void editTemplateCatalog(TemplateCatalogParam templateCatalog);

    List<TreeNode> getTemplateCatalog();


    String getNewTemplateFileVersion(String templateCode);

    FileTemplate getFileTemplateByTemplateCode(String templateCode);

    void addTemplateFile(FileTemplateParams fileTemplateParams) throws IOException;


    void editFileTemplate(FileTemplateParams editParam) throws Exception;

    void uploadTemplateFile(MultipartFile file,
                            Integer templateId) throws IOException;
}
