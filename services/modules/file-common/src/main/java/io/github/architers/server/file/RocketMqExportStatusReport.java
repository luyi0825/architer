package io.github.architers.server.file;

import io.github.architers.server.file.domain.dto.ExportStatusReportDTO;
import io.github.architers.server.file.enums.TaskRecordStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Date;

/**
 * rocketmq导出状态上报
 *
 * @author luyi
 */
@Slf4j
public class RocketMqExportStatusReport implements ExportStatusReport {

    private RocketMQTemplate rocketMQTemplate;

    public RocketMqExportStatusReport(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @Override
    public void processing(ExportStatusReportDTO exportStatusReportDTO) {
        exportStatusReportDTO.setStatus(TaskRecordStatusEnum.PROCESSING.getStatus());
        exportStatusReportDTO.setStartTime(new Date());
        sendMessage(exportStatusReportDTO);
    }

    @Override
    public void failed(ExportStatusReportDTO exportStatusReportDTO) {
        exportStatusReportDTO.setStatus(TaskRecordStatusEnum.FAILED.getStatus());
        exportStatusReportDTO.setEndTime(new Date());
        sendMessage(exportStatusReportDTO);

    }


    @Override
    public void cancel(ExportStatusReportDTO exportStatusReportDTO) {
        exportStatusReportDTO.setStatus(TaskRecordStatusEnum.CANCEL.getStatus());
        sendMessage(exportStatusReportDTO);
    }

    @Override
    public void finished(ExportStatusReportDTO exportStatusReportDTO) {
        exportStatusReportDTO.setStatus(TaskRecordStatusEnum.FINISHED.getStatus());
        sendMessage(exportStatusReportDTO);
        exportStatusReportDTO.setEndTime(new Date());
    }

    private void sendMessage(ExportStatusReportDTO exportStatusReportDTO) {
        Message<ExportStatusReportDTO> message = MessageBuilder.withPayload(exportStatusReportDTO)
                .setHeader(RocketMQHeaders.PREFIX + RocketMQHeaders.KEYS, exportStatusReportDTO.getRequestId())
                .setHeader(RocketMQHeaders.PREFIX + RocketMQHeaders.TAGS, exportStatusReportDTO.getTaskCode())
                .build();

        SendResult sendResult = rocketMQTemplate.syncSendOrderly("export_file_status_report", message, exportStatusReportDTO.getTaskCode());
        if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.error("上报导出任务状态失败:{}-{}", exportStatusReportDTO, sendResult);
        }
    }


}
