package io.github.architers.server.file.api;

import cn.hutool.extra.spring.SpringUtil;
import io.github.architers.context.model.TreeNode;
import io.github.architers.context.valid.group.AddGroup;
import io.github.architers.context.valid.group.EditGroup;
import io.github.architers.server.file.domain.param.TemplateCatalogParam;
import io.github.architers.server.file.domain.param.FileTemplateParams;
import io.github.architers.server.file.service.FileTemplateService;
import io.github.architers.server.file.service.ImportTemplateFileService;
import io.github.architers.server.file.utils.TempFileUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 导入模板
 *
 * @author luyi
 */
@RestController
@RequestMapping("/fileTemplateApi")
public class FileTemplateApi {

    @Resource
    private FileTemplateService fileTemplateService;


    /**
     * 添加目录
     */
    @PostMapping("/addTemplateCatalog")
    public void addTemplateCatalog(@RequestBody @Validated(AddGroup.class) TemplateCatalogParam templateCatalog) {
        fileTemplateService.addTemplateCatalog(templateCatalog);
    }

    /**
     * 编辑目录
     */
    @PutMapping("/editTemplateCatalog")
    public void editTemplateCatalog(@RequestBody @Validated(EditGroup.class) TemplateCatalogParam templateCatalog) {
        fileTemplateService.editTemplateCatalog(templateCatalog);
    }

    /**
     * 查询目录树
     */
    @GetMapping("/getTemplateCatalog")
    public List<TreeNode> getTemplateCatalog() {
        return fileTemplateService.getTemplateCatalog();
    }

    /**
     * 添加模板目录
     */
    @PutMapping("/addTemplateFile")
    public void addTemplateFile(@RequestBody @Validated(AddGroup.class) FileTemplateParams fileTemplateParams) throws IOException {
        fileTemplateService.addTemplateFile(fileTemplateParams);
    }

    /**
     * 测试上传模版文件
     */
    @PostMapping("/testUploadFile")
    public void testUploadFile(@RequestParam("templateCode") String templateCode,
                               MultipartFile file) throws IOException {
        ImportTemplateFileService importTemplateFileService = SpringUtil.getBean(ImportTemplateFileService.class);
        File tempFile = null;
        try {
            tempFile = TempFileUtil.generateTempFile(Objects.requireNonNull(file.getOriginalFilename()));
            FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
            importTemplateFileService.importTemplateFile(tempFile, templateCode);
        } finally {
            //删除文件
            FileUtils.deleteQuietly(tempFile);
        }
    }



    /**
     * 编辑模板文件
     */
    @PutMapping("/editFileTemplate")
    public void editFileTemplate(@RequestBody @Validated(EditGroup.class)  FileTemplateParams editParam) throws Exception {
        fileTemplateService.editFileTemplate(editParam);
    }

    /**
     * 上传模板文件
     *
     * @param file       上传的模板文件
     * @param templateId 模板ID
     */
    @PostMapping("/uploadTemplateFile")
    public void uploadTemplateFile(MultipartFile file,
                                   @RequestParam("templateId") Integer templateId) throws IOException {
        fileTemplateService.uploadTemplateFile(file, templateId);
    }

    /**
     * 得到最新的模板文件版本
     *
     * @return 模板文件版本
     */
    @GetMapping("/getNewTemplateFileVersion")
    public String getNewTemplateFileVersion(@RequestParam("templateCode") String templateCode) {
        return fileTemplateService.getNewTemplateFileVersion(templateCode);
    }

}
