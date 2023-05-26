package io.github.architers.server.file.api;

import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.server.file.domain.param.ExecuteTaskParam;
import io.github.architers.server.file.domain.param.TaskRecordsQueryParam;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
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
     * 添加任务
     */
    @PostMapping("/executeTask")
    public boolean executeTask(@RequestBody @Validated ExecuteTaskParam executeTaskParam) {
        return taskService.sendTask(executeTaskParam);
    }

    /**
     * 分页查询导出任务记录
     */
    @PostMapping("/getExportRecordsByPage")
    public PageResult<FileTaskExportRecord> getTaskRecordsByPage(@RequestBody @Validated PageRequest<TaskRecordsQueryParam> pageRequest) {
        return taskRecordService.getTaskRecordsByPage(pageRequest);
    }




}
