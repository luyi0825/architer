package io.github.architers.server.file.api;

import io.github.architers.context.model.TreeNode;
import io.github.architers.context.utils.NodeTreeUtils;
import io.github.architers.context.valid.group.AddGroup;
import io.github.architers.context.valid.group.EditGroup;
import io.github.architers.server.file.domain.dto.TemplateCatalogDTO;
import io.github.architers.server.file.domain.dto.TemplateDTO;
import io.github.architers.server.file.domain.param.FileTemplateAddParams;
import io.github.architers.server.file.service.ImportTemplateService;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 导入模板
 *
 * @author luyi
 */
@RestController
@RequestMapping("/fileTemplateApi")
public class FileTemplateApi {

    @Resource
    private ImportTemplateService importTemplateService;

    /**
     * 添加模板目录
     */
    @PutMapping("/addTemplateFile")
    public void addTemplateFile(@Valid FileTemplateAddParams fileTemplateAddParams, MultipartFile file) throws IOException {
        Assert.notNull(file, "模板文件不能为空");
        importTemplateService.addTemplateFile(fileTemplateAddParams, file);
    }

    /**
     * 添加目录
     */
    @PostMapping("/addTemplateCatalog")
    public void addTemplateCatalog(@RequestBody @Validated(AddGroup.class) TemplateCatalogDTO templateCatalog) {
        importTemplateService.addTemplateCatalog(templateCatalog);
    }

    /**
     * 编辑目录
     */
    @PutMapping("/editTemplateCatalog")
    public void editTemplateCatalog(@RequestBody @Validated(EditGroup.class) TemplateCatalogDTO templateCatalog) {
        importTemplateService.editTemplateCatalog(templateCatalog);
    }

    /**
     * 查询目录树
     */
    @GetMapping("/getTemplateCatalog")
    public List<TreeNode> getTemplateCatalog() {
        return importTemplateService.getTemplateCatalog();
    }

    /**
     * 新建模板文件
     */
    @PostMapping("/addTemplate")
    public void addTemplate(@RequestBody @Validated(AddGroup.class) TemplateDTO templateDTO) {
        importTemplateService.addTemplate(templateDTO);
    }

    /**
     * 编辑模板文件
     */
    @PostMapping("/editTemplate")
    public void editTemplate(@RequestBody @Validated(EditGroup.class) TemplateDTO templateDTO) {
        importTemplateService.editTemplate(templateDTO);
    }

    /**
     * 上传模板文件
     *
     * @param file       上传的模板文件
     * @param templateId 模板ID
     */
    @PutMapping("/uploadTemplateFile")
    public void uploadTemplateFile(MultipartFile file,
                                   @RequestParam("templateId") Integer templateId,
                                   @RequestParam("refreshVersion") boolean refreshVersion) throws IOException {
        importTemplateService.uploadTemplateFile(file, templateId, refreshVersion);
    }

    /**
     * 得到最新的模板文件版本
     *
     * @return 模板文件版本
     */
    @GetMapping("/getNewTemplateFileVersion")
    public String getNewTemplateFileVersion(@RequestParam("templateCode") String templateCode) {
        return importTemplateService.getNewTemplateFileVersion(templateCode);
    }

}
