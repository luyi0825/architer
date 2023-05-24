package io.github.architers.server.file.task;


import io.github.architers.server.file.domain.dto.TaskRecordDTO;
import io.github.architers.server.file.model.entity.FileTaskRecord;
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
            FileTaskRecord fileTaskRecord = new FileTaskRecord();
            fileTaskRecord.setRemark(taskRecordDTO.getRemark());
            fileTaskRecord.setResultUrl(taskRecordDTO.getResultUrl());
            fileTaskRecord.setStatus(taskRecordDTO.getStatus().getStatus());
            fileTaskRecord.setSuccessNum(taskRecordDTO.getSuccessNum());
            fileTaskRecord.setTotalNum(taskRecordDTO.getTotalNum());
            fileTaskRecord.setId(fileTaskRecord.getId());
            taskRecordService.updateProcessingResultWithOptimisticLock(fileTaskRecord);
        };
    }


    @Resource
    private ITaskRecordService taskRecordService;

}
