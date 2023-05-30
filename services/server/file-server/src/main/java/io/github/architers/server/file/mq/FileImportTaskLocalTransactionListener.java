package io.github.architers.server.file.mq;

import io.github.architers.context.exception.BusException;
import io.github.architers.context.utils.JsonUtils;
import io.github.architers.server.file.domain.entity.FileTaskImportRecord;
import io.github.architers.server.file.enums.TaskRecordStatusEnum;
import io.github.architers.server.file.mq.mq.LocalTransactionExecute;
import io.github.architers.server.file.service.IFileTaskImportRecordService;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class FileImportTaskLocalTransactionListener implements LocalTransactionExecute {

    @Resource
    private IFileTaskImportRecordService fileTaskImportRecordService;

    @Override
    public String getBusinessKey() {
        return "file_import_task";
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message<?> msg) {
        FileTaskImportRecord fileTaskExportRecord = JsonUtils.readValue((byte[]) msg.getPayload(), FileTaskImportRecord.class);

        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RocketMQLocalTransactionState executeLocalTransaction(Message<?> msg, Object arg) {
        TaskRecordStatusEnum taskRecordStatusEnum = (TaskRecordStatusEnum) arg;
        if (TaskRecordStatusEnum.IN_LINE.equals(taskRecordStatusEnum)) {
            FileTaskImportRecord fileTaskExportRecord = JsonUtils.readValue((byte[]) msg.getPayload(), FileTaskImportRecord.class);
            if (fileTaskImportRecordService.save(fileTaskExportRecord)) {
                return RocketMQLocalTransactionState.COMMIT;
            }
        }
        throw new BusException("添加导出文件任务失败");


    }
}
