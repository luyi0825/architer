package io.github.arthiters.test.file.service.impl;

import io.github.architers.server.file.FileTaskTemplate;
import io.github.architers.server.file.domain.dto.ImportStatusReportDTO;
import io.github.architers.server.file.domain.params.ImportTaskParam;
import io.github.arthiters.test.file.service.IExportImportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.function.BiFunction;

@Service
public class ExportImportServiceImpl implements IExportImportService {

    @Resource
    private FileTaskTemplate fileTaskTemplate;

    @Override
    public void importData(ImportTaskParam<Map<String, Object>> importTaskParam) {

        fileTaskTemplate.importFile(importTaskParam, new BiFunction<ImportTaskParam<?>, ImportStatusReportDTO, Boolean>() {
            @Override
            public Boolean apply(ImportTaskParam<?> importTaskParam, ImportStatusReportDTO importStatusReportDTO) {
                return true;
            }
        });
        System.out.println(importTaskParam.getTaskCode());
    }
}
