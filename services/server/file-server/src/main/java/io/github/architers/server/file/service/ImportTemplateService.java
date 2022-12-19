package io.github.architers.server.file.service;

import io.github.architers.context.utils.NodeTreeUtils;
import io.github.architers.server.file.domain.dto.TemplateCatalogDTO;
import io.github.architers.server.file.domain.dto.TemplateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImportTemplateService {
    /**
     * 添加模板目录
     *
     * @param templateCatalog 需要添加的模板目录信息
     */
    void addTemplateCatalog(TemplateCatalogDTO templateCatalog);

    void editTemplateCatalog(TemplateCatalogDTO templateCatalog);

    List<NodeTreeUtils.TreeNode> getTemplateCatalog();

    void addTemplate(TemplateDTO templateDTO);

    void editTemplate(TemplateDTO templateDTO);

    void uploadTemplateFile(MultipartFile file,
                            Integer templateId,
                            boolean refreshVersion
                            ) throws IOException;

    String getNewTemplateFileVersion(String templateCode);
}
