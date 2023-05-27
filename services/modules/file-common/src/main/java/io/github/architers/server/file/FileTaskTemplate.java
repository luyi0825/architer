package io.github.architers.server.file;

import io.github.architers.server.file.domain.dto.ExportStatusReportDTO;
import io.github.architers.server.file.domain.dto.ImportStatusReportDTO;
import io.github.architers.server.file.domain.params.ExportTaskParam;
import io.github.architers.server.file.domain.params.ImportTaskParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@Service
@Slf4j
public class FileTaskTemplate {


    private ExportStatusReport exportStatusReport;

    private ImportStatusReport importStatusReport;

    public void exportFile(ExportTaskParam<?> exportTaskParam, BiFunction<ExportTaskParam<?>,ExportStatusReportDTO,Boolean> exportTaskExecute) {
        ExportStatusReportDTO exportStatusReportDTO = new ExportStatusReportDTO();
        try {
            exportStatusReportDTO.setRequestId(exportTaskParam.getRequestId());
            exportStatusReportDTO.setTaskCode(exportTaskParam.getTaskCode());
            //开始导出
            exportStatusReport.processing(exportStatusReportDTO);
            boolean success = exportTaskExecute.apply(exportTaskParam, exportStatusReportDTO);
            if (success) {
                exportStatusReport.finished(exportStatusReportDTO);
            } else {
                exportStatusReport.failed(exportStatusReportDTO);
            }
        } catch (Exception e) {
            log.error("导出失败", e);
            exportStatusReportDTO.setRemark("导出失败:" + e.getMessage());
            exportStatusReport.failed(exportStatusReportDTO);
        }
    }

    public void importFile(ImportTaskParam<?> importTaskParam, BiFunction<ImportTaskParam<?>,ImportStatusReportDTO,Boolean> importTaskFunction) {
        ImportStatusReportDTO importStatusReportDTO = new ImportStatusReportDTO();
        try {
            importStatusReportDTO.setRequestId(importTaskParam.getRequestId());
            importStatusReportDTO.setTaskCode(importTaskParam.getTaskCode());
            //开始导入
            importStatusReport.processing(importStatusReportDTO);
            boolean success = importTaskFunction.apply(importTaskParam, importStatusReportDTO);
            if (success) {
                importStatusReport.finished(importStatusReportDTO);
            } else {
                importStatusReport.failed(importStatusReportDTO);
            }
        } catch (Exception e) {
            log.error("导入失败", e);
            importStatusReportDTO.setRemark("导入失败:" + e.getMessage());
            importStatusReport.failed(importStatusReportDTO);
        }
    }

    @Autowired(required = false)
    public void setExportStatusReport(ExportStatusReport exportStatusReport) {
        this.exportStatusReport = exportStatusReport;
    }

    @Autowired(required = false)
    public void setImportStatusReport(ImportStatusReport importStatusReport) {
        this.importStatusReport = importStatusReport;
    }
}
