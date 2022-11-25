package io.github.architers.server.file.task;


import io.github.architers.server.file.domain.entity.TaskRecord;
import io.github.architers.server.file.service.ITaskRecordService;
import lombok.SneakyThrows;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.util.function.Consumer;

/**
 * 消费文件任务处理结果
 *
 * @author luyi
 */
@Component
@MessageMapping(value = "fileTaskConsumer")
public class FileTaskResultConsumer implements Consumer<TaskRecord> {


    @Resource
    private ITaskRecordService taskRecordService;

    @SneakyThrows
    @Override
    public void accept(TaskRecord taskRecord) {
        taskRecordService.updateProcessingResultWithOptimisticLock(taskRecord);
    }
}
