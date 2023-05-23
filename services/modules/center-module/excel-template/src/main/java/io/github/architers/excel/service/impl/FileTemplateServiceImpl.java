package io.github.architers.excel.service.impl;

import io.github.architers.domain.param.FileTemplateAddParams;
import io.github.architers.excel.service.IFileTemplateService;
import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author luyi
 * 文件模板信息
 */
@Service
public class FileTemplateServiceImpl implements IFileTemplateService {


    @Override
    public void addTemplateFile(FileTemplateAddParams fileTemplateAddParams, MultipartFile file) throws IOException {


    }
}
