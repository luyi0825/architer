package io.github.architers.server.file.task;


import io.github.architers.server.file.domain.dto.TaskRecordDTO;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
import io.github.architers.server.file.service.ITaskRecordService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.annotation.Resource;
import java.util.function.Consumer;

/**
 * 消费文件任务处理结果
 *
 * @author luyi
 */
@Configuration
public class FileTaskResultConsumer {


    @Bean
    public Consumer<TaskRecordDTO> fileTaskResult() {
        return taskRecordDTO -> {
            FileTaskExportRecord fileTaskExportRecord = new FileTaskExportRecord();
            fileTaskExportRecord.setRemark(taskRecordDTO.getRemark());
            fileTaskExportRecord.setResultUrl(taskRecordDTO.getResultUrl());
            fileTaskExportRecord.setStatus(taskRecordDTO.getStatus().getStatus());
            fileTaskExportRecord.setSuccessNum(taskRecordDTO.getSuccessNum());
            fileTaskExportRecord.setTotalNum(taskRecordDTO.getTotalNum());
            fileTaskExportRecord.setId(fileTaskExportRecord.getId());
            taskRecordService.updateProcessingResultWithOptimisticLock(fileTaskExportRecord);
        };
    }


    @Resource
    private ITaskRecordService taskRecordService;

}
