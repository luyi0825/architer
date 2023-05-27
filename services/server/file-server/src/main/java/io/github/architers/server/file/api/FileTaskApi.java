package io.github.architers.server.file.api;

import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.server.file.domain.param.ExecuteTaskParam;
import io.github.architers.server.file.domain.param.TaskRecordsPageParam;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
import io.github.architers.server.file.eums.TransactionMessageResult;
import io.github.architers.server.file.service.ITaskRecordService;
import io.github.architers.server.file.service.TaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author luyi
 */
@RestController
@RequestMapping("/taskApi")
public class FileTaskApi {

    @Resource
    private TaskService taskService;

    @Resource
    private ITaskRecordService taskRecordService;


    /**
     * 执行导出
     */
    @PostMapping("/executeExportTask")
    public TransactionMessageResult executeExportTask(@RequestBody @Validated ExecuteTaskParam executeTaskParam) {
        return taskService.executeExportTask(executeTaskParam);
    }

    /**
     * 分页查询导出任务记录
     */
    @PostMapping("/getExportRecordsByPage")
    public PageResult<FileTaskExportRecord> getTaskRecordsByPage(@RequestBody @Validated PageRequest<TaskRecordsPageParam> pageRequest) {
        return taskRecordService.getTaskRecordsByPage(pageRequest);
    }




}
