package io.github.architers.excel.service;

import io.github.architers.domain.param.FileTemplateAddParams;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author luyi
 * 文件模板信息
 */
public interface IFileTemplateService {


    void addTemplateFile(FileTemplateAddParams fileTemplateAddParams, MultipartFile file) throws IOException;
}
