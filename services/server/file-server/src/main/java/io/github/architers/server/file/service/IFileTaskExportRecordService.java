package io.github.architers.server.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;

public interface IFileTaskExportRecordService extends IService<FileTaskExportRecord> {

    /**
     * 通过requestId更新
     */
    boolean updateByRequestId(FileTaskExportRecord fileTaskExportRecord);
}
