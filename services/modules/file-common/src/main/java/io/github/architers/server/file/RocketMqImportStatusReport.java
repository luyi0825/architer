package io.github.architers.server.file;

import io.github.architers.server.file.domain.dto.ImportStatusReportDTO;
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
public class RocketMqImportStatusReport implements ImportStatusReport {

    private RocketMQTemplate rocketMQTemplate;

    public RocketMqImportStatusReport(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    @Override
    public void processing(ImportStatusReportDTO importStatusReportDTO) {
        importStatusReportDTO.setStatus(TaskRecordStatusEnum.PROCESSING.getStatus());
        importStatusReportDTO.setStartTime(new Date());
        sendMessage(importStatusReportDTO);
    }

    @Override
    public void failed(ImportStatusReportDTO importStatusReportDTO) {
        importStatusReportDTO.setStatus(TaskRecordStatusEnum.FAILED.getStatus());
        importStatusReportDTO.setEndTime(new Date());
        sendMessage(importStatusReportDTO);

    }

    @Override
    public void cancel(ImportStatusReportDTO importStatusReportDTO) {
        importStatusReportDTO.setStatus(TaskRecordStatusEnum.CANCEL.getStatus());
        sendMessage(importStatusReportDTO);
    }

    @Override
    public void finished(ImportStatusReportDTO importStatusReportDTO) {
        importStatusReportDTO.setStatus(TaskRecordStatusEnum.FINISHED.getStatus());
        sendMessage(importStatusReportDTO);
        importStatusReportDTO.setEndTime(new Date());
    }

    private void sendMessage(ImportStatusReportDTO statusReportDTO) {
        Message<ImportStatusReportDTO> message = MessageBuilder.withPayload(statusReportDTO)
                .setHeader(RocketMQHeaders.PREFIX + RocketMQHeaders.KEYS, statusReportDTO.getRequestId())
                .setHeader(RocketMQHeaders.PREFIX + RocketMQHeaders.TAGS, statusReportDTO.getTaskCode())
                .build();

        SendResult sendResult = rocketMQTemplate.syncSendOrderly("import_file_status_report", message, statusReportDTO.getTaskCode());
        if (!SendStatus.SEND_OK.equals(sendResult.getSendStatus())) {
            log.error("上报导出任务状态失败:{}-{}", statusReportDTO, sendResult);
        }
    }

}
