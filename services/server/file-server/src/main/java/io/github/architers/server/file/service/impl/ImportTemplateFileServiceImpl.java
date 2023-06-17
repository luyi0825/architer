package io.github.architers.server.file.service.impl;

import com.alibaba.excel.EasyExcel;
import io.github.architers.context.exception.BusException;
import io.github.architers.context.exception.BusErrorException;
import io.github.architers.server.file.domain.entity.FileTemplate;
import io.github.architers.server.file.domain.entity.FileTemplateCheckRowInfo;
import io.github.architers.server.file.service.ImportTemplateFileService;
import io.github.architers.server.file.service.FileTemplateService;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Service
public class ImportTemplateFileServiceImpl implements ImportTemplateFileService {

    @Resource
    private FileTemplateService fileTemplateService;

    @Override
    public void importTemplateFile(File file, String templateCode) throws FileNotFoundException {

        FileTemplate fileTemplate = fileTemplateService.getFileTemplateByTemplateCode(templateCode);
        if (fileTemplate == null) {
            throw new BusErrorException("模板文件不存在");
        }
        InputStream inputStream = null;

        try {
            FileTemplateCheckRowInfo checkRowInfo = fileTemplate.getCheckRowInfo();
            if (Boolean.TRUE.equals(checkRowInfo.getEnableCheck())) {
                inputStream = new FileInputStream(file);
                EasyExcelCheckRowReadListener readListener = new EasyExcelCheckRowReadListener(checkRowInfo.getStartRow(), checkRowInfo.getEndRow());
                EasyExcel.read(file).registerReadListener(readListener).autoCloseStream(false).doReadAll();
                if (!checkRowInfo.getBase64RowStr().equals(readListener.getBase64RowData())) {
                    throw new BusException("导入模板检验文件表头失败，请下载最新的模板");
                }
            }
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

    }


}
