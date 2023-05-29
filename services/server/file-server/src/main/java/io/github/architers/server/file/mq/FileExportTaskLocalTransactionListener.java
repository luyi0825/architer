package io.github.architers.server.file.mq;

import io.github.architers.context.exception.BusException;
import io.github.architers.context.utils.JsonUtils;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
import io.github.architers.server.file.enums.TaskRecordStatusEnum;
import io.github.architers.server.file.mq.mq.LocalTransactionExecute;
import io.github.architers.server.file.service.IFileTaskExportRecordService;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class FileExportTaskLocalTransactionListener implements LocalTransactionExecute {

    @Resource
    private IFileTaskExportRecordService fileTaskExportRecordService;

    @Override
    public String getBusinessKey() {
        return "file_export_task";
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message<?> msg) {
        FileTaskExportRecord fileTaskExportRecord = JsonUtils.readValue((byte[]) msg.getPayload(), FileTaskExportRecord.class);

        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RocketMQLocalTransactionState executeLocalTransaction(Message<?> msg, Object arg) {
        TaskRecordStatusEnum taskRecordStatusEnum = (TaskRecordStatusEnum) arg;

        if (TaskRecordStatusEnum.IN_LINE.equals(taskRecordStatusEnum)) {
            FileTaskExportRecord fileTaskExportRecord = JsonUtils.readValue((byte[]) msg.getPayload(), FileTaskExportRecord.class);
            if (fileTaskExportRecordService.save(fileTaskExportRecord)) {
                return RocketMQLocalTransactionState.COMMIT;
            }
        }
        throw new BusException("添加导出文件任务失败");


    }
}
