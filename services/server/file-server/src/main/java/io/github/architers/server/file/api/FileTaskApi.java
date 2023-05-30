package io.github.architers.server.file.api;

import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.server.file.domain.entity.FileTaskImportRecord;
import io.github.architers.server.file.domain.param.ExecuteTaskParam;
import io.github.architers.server.file.domain.param.TaskRecordsPageParam;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
import io.github.architers.server.file.eums.TransactionMessageResult;
import io.github.architers.server.file.service.IFileTaskExportRecordService;
import io.github.architers.server.file.service.IFileTaskImportRecordService;
import io.github.architers.server.file.service.TaskService;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * @author luyi
 */
@RestController
@RequestMapping("/taskApi")
public class FileTaskApi {

    @Resource
    private TaskService taskService;

    @Resource
    private IFileTaskExportRecordService fileTaskExportRecordService;

    @Resource
    private IFileTaskImportRecordService fileTaskImportRecordService;


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
    public PageResult<FileTaskExportRecord> getExportRecordsByPage(@RequestBody @Validated PageRequest<TaskRecordsPageParam> pageRequest) {
        return fileTaskExportRecordService.getTaskRecordsByPage(pageRequest);
    }


    /**
     * 分页查询导出任务记录
     */
    @PostMapping("/getImportRecordsByPage")
    public PageResult<FileTaskImportRecord> getImportRecordsByPage(@RequestBody @Validated PageRequest<TaskRecordsPageParam> pageRequest) {
        return fileTaskImportRecordService.getImportRecordsByPage(pageRequest);
    }

    /**
     * 分页查询导出任务记录
     */
    @PostMapping("/executeImportTask")
    public TransactionMessageResult executeImportTask(@Validated ExecuteTaskParam executeTaskParam,
                                                      @NotNull(message = "导入文件不能为空") MultipartFile file) throws IOException {
        Assert.notNull(file,"导入文件不能为空");
        return taskService.executeImportTask(executeTaskParam, file);
    }


}
