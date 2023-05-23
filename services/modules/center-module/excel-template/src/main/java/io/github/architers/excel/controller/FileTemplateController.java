package io.github.architers.excel.controller;

import io.github.architers.domain.param.FileTemplateAddParams;
import io.github.architers.excel.service.IFileTemplateService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author luyi
 */
@RestController
public class FileTemplateController {

    @Resource
    private IFileTemplateService fileTemplateService;

    /**
     * 添加模板目录
     */
    @PutMapping("/addTemplateFile")
    public void addTemplateFile(@Valid FileTemplateAddParams fileTemplateAddParams, MultipartFile file) throws IOException {
        Assert.notNull(file, "模板文件不能为空");
        fileTemplateService.addTemplateFile(fileTemplateAddParams, file);
    }
}
