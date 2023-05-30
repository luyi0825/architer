package io.github.architers.server.file.mq;


import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
import io.github.architers.server.file.domain.entity.FileTaskImportRecord;
import io.github.architers.server.file.enums.TaskRecordStatusEnum;
import io.github.architers.server.file.eums.TransactionMessageResult;
import io.github.architers.server.file.mq.mq.LocalTransactionBusinessKey;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FileExportMessageSender {

    private String topic = "file_export_task";

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public TransactionMessageResult sendExportTaskMessage(FileTaskExportRecord fileTaskExportRecord) {
        Message<FileTaskExportRecord> exportRecordMessage = MessageBuilder.withPayload(fileTaskExportRecord)
                .setHeader(LocalTransactionBusinessKey.BUSINESS_KEY_HEADER, "file_export_task")
                .setHeader(RocketMQHeaders.PREFIX+RocketMQHeaders.KEYS,fileTaskExportRecord.getRequestId())
                .build();
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(topic, exportRecordMessage, TaskRecordStatusEnum.IN_LINE);
        return TransactionMessageResult.of(transactionSendResult.getLocalTransactionState());
    }

    /**
     * 发送导入文件的消息
     */

    public TransactionMessageResult sendImportTaskMessage(FileTaskImportRecord fileTaskImportRecord) {
        Message<FileTaskImportRecord> importRecordMessage = MessageBuilder.withPayload(fileTaskImportRecord)
                .setHeader(LocalTransactionBusinessKey.BUSINESS_KEY_HEADER, "file_import_task")
                .setHeader(RocketMQHeaders.PREFIX+RocketMQHeaders.KEYS,fileTaskImportRecord.getRequestId())
                .build();
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction("file_import_task", importRecordMessage, TaskRecordStatusEnum.IN_LINE);
        return TransactionMessageResult.of(transactionSendResult.getLocalTransactionState());
    }
}
