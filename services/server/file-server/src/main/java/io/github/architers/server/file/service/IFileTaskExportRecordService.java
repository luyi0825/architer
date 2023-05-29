package io.github.architers.server.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.server.file.domain.param.TaskRecordsPageParam;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;

/**
 * @author Administrator
 */
public interface IFileTaskExportRecordService extends IService<FileTaskExportRecord> {

    /**
     * 通过requestId更新
     */
    boolean updateByRequestId(FileTaskExportRecord fileTaskExportRecord);

    /**
     * 初始化任务
     *
     * @param taskCode 任务编码
     * @return 任务的主键ID
     */
    Long initTask(String taskCode);


    /**
     * 更新处理中的任务的结果(乐观锁)
     *
     * @param fileTaskExportRecord 更新的数据
     */
    void updateProcessingResultWithOptimisticLock(FileTaskExportRecord fileTaskExportRecord);

    PageResult<FileTaskExportRecord> getTaskRecordsByPage(PageRequest<TaskRecordsPageParam> pageRequest);

}
