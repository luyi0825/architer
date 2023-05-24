package io.github.architers.server.file.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.ReadListener;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.exception.BusLogException;
import io.github.architers.server.file.model.entity.FileTemplate;
import io.github.architers.server.file.model.entity.FileTemplateCheckFileVersion;
import io.github.architers.server.file.model.entity.FileTemplateCheckRowInfo;
import io.github.architers.server.file.service.ImportTemplateFileService;
import io.github.architers.server.file.service.ImportTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Map;

@Service
public class ImportTemplateFileServiceImpl implements ImportTemplateFileService {

    @Resource
    private ImportTemplateService importTemplateService;

    @Override
    public void importTemplateFile(File file, String templateCode) {

        FileTemplate fileTemplate = importTemplateService.getFileTemplateByTemplateCode(templateCode);
        if (fileTemplate == null) {
            throw new BusLogException("文件目录不存在");
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
