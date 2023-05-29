package io.github.architers.server.file.mq.consumer;

import io.github.architers.server.file.domain.dto.ExportStatusReportDTO;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
import io.github.architers.server.file.service.IFileTaskExportRecordService;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@RocketMQMessageListener(consumeMode = ConsumeMode.ORDERLY, topic = "export_file_status_report", consumerGroup = "export_file_status_report_consumer",
        consumeThreadNumber = 10)
public class FileExportStatusReportConsumer implements RocketMQListener<ExportStatusReportDTO> {

    @Resource
    private IFileTaskExportRecordService fileTaskExportRecordService;

    @Override
    public void onMessage(ExportStatusReportDTO statusReport) {
        FileTaskExportRecord fileTaskExportRecord = new FileTaskExportRecord();
        fileTaskExportRecord.setRequestId(statusReport.getRequestId());
        fileTaskExportRecord.setStatus(statusReport.getStatus());
        fileTaskExportRecord.setResultUrl(statusReport.getResultUrl());
        fileTaskExportRecord.setSuccessNum(statusReport.getSuccessNum());
        fileTaskExportRecord.setUpdateTime(new Date());
        fileTaskExportRecord.setStartTime(statusReport.getStartTime());
        fileTaskExportRecord.setEndTime(statusReport.getEndTime());
        fileTaskExportRecord.setEndTime(statusReport.getEndTime());
        fileTaskExportRecord.setFileName(statusReport.getFileName());
        fileTaskExportRecordService.updateByRequestId(fileTaskExportRecord);
    }
}
