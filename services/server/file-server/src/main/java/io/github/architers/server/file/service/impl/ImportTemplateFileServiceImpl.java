package io.github.architers.server.file.service.impl;

import com.alibaba.excel.EasyExcel;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.exception.BusLogException;
import io.github.architers.server.file.model.entity.FileTemplate;
import io.github.architers.server.file.model.entity.FileTemplateCheckFileVersion;
import io.github.architers.server.file.model.entity.FileTemplateCheckRowInfo;
import io.github.architers.server.file.service.ImportTemplateFileService;
import io.github.architers.server.file.service.FileTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

@Service
public class ImportTemplateFileServiceImpl implements ImportTemplateFileService {

    @Resource
    private FileTemplateService fileTemplateService;

    @Override
    public void importTemplateFile(File file, String templateCode) {

        FileTemplate fileTemplate = fileTemplateService.getFileTemplateByTemplateCode(templateCode);
        if (fileTemplate == null) {
            throw new BusLogException("模板文件不存在");
        }
        FileTemplateCheckFileVersion checkFileVersion = fileTemplate.getCheckFileVersion();
        if (Boolean.TRUE.equals(checkFileVersion.getEnableCheck())) {

        }

        FileTemplateCheckRowInfo checkRowInfo = fileTemplate.getCheckRowInfo();
        if (Boolean.TRUE.equals(checkRowInfo.getEnableCheck())) {
            EasyExcelCheckRowReadListener readListener = new EasyExcelCheckRowReadListener(checkRowInfo.getStartRow(), checkRowInfo.getEndRow());
            EasyExcel.read(file).registerReadListener(readListener).doReadAll();
            if (!checkRowInfo.getBase64RowStr().equals(readListener.getBase64RowData())) {
                throw new BusException("导入模板检验文件表头失败，请下载最新的模板");
            }
        }
    }


}
