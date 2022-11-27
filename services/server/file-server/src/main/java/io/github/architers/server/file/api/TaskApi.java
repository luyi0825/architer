package io.github.architers.server.file.api;

import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.server.file.domain.dto.ExecuteTaskParam;
import io.github.architers.server.file.domain.dto.TaskRecordsQueryDTO;
import io.github.architers.server.file.domain.entity.FileTaskRecord;
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
public class TaskApi {

    @Resource
    private TaskService taskService;

    @Resource
    private ITaskRecordService taskRecordService;


    /**
     * 添加任务
     */
    @PostMapping("/executeTask")
    public boolean executeTask(@RequestBody @Validated ExecuteTaskParam executeTaskParam) throws InterruptedException {
        return taskService.sendTask(executeTaskParam);
    }

    /**
     * 分页查询任务记录
     */
    @PostMapping("/getTaskRecordsByPage")
    public PageResult<FileTaskRecord> getTaskRecordsByPage(@RequestBody @Validated PageRequest<TaskRecordsQueryDTO> pageRequest) {
        return taskRecordService.getTaskRecordsByPage(pageRequest);
    }


}
