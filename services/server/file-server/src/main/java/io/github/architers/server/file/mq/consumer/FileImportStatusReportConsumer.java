package io.github.architers.server.file.mq.consumer;

import io.github.architers.server.file.domain.dto.ExportStatusReportDTO;
import io.github.architers.server.file.domain.dto.ImportStatusReportDTO;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
import io.github.architers.server.file.domain.entity.FileTaskImportRecord;
import io.github.architers.server.file.service.IFileTaskExportRecordService;
import io.github.architers.server.file.service.IFileTaskImportRecordService;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@RocketMQMessageListener(consumeMode = ConsumeMode.ORDERLY, topic = "import_file_status_report", consumerGroup = "import_file_status_report_consumer",
        consumeThreadNumber = 10)
public class FileImportStatusReportConsumer implements RocketMQListener<ImportStatusReportDTO> {

    @Resource
    private IFileTaskImportRecordService fileTaskImportRecordService;

    @Override
    public void onMessage(ImportStatusReportDTO statusReport) {
        FileTaskImportRecord fileTaskImportRecord = new FileTaskImportRecord();
        fileTaskImportRecord.setRequestId(statusReport.getRequestId());
        fileTaskImportRecord.setStatus(statusReport.getStatus());
        fileTaskImportRecord.setErrorUrl(statusReport.getErrorUrl());
        fileTaskImportRecord.setTotalNum(statusReport.getTotalNum());
        fileTaskImportRecord.setSuccessNum(statusReport.getSuccessNum());
        fileTaskImportRecord.setUpdateTime(new Date());
        fileTaskImportRecord.setStartTime(statusReport.getStartTime());
        fileTaskImportRecord.setEndTime(statusReport.getEndTime());
        fileTaskImportRecord.setEndTime(statusReport.getEndTime());
        fileTaskImportRecordService.updateByRequestId(fileTaskImportRecord);
    }
}
