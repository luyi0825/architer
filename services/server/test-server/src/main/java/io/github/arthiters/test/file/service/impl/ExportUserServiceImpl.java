package io.github.arthiters.test.file.service.impl;

import io.github.architers.server.file.FileTaskTemplate;
import io.github.architers.server.file.domain.dto.ExportStatusReportDTO;
import io.github.architers.server.file.domain.params.ExportTaskParam;
import io.github.arthiters.test.file.domain.params.ExportUser;
import io.github.arthiters.test.file.service.IExportUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ExportUserServiceImpl implements IExportUserService {

    @Resource
    private FileTaskTemplate fileTaskTemplate;

    @Override
    public void exportUse(ExportTaskParam<ExportUser> exportTaskParam) {
        fileTaskTemplate.exportFile(exportTaskParam, (exportTaskParam1, statusReportDTO) -> {
            ExportStatusReportDTO exportStatusReportDTO = statusReportDTO;
            exportStatusReportDTO.setSuccessNum(1000);
            return true;
        });
    }


}
