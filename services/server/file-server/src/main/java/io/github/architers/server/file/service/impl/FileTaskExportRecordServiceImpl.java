package io.github.architers.server.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.architers.server.file.domain.entity.FileTaskExportRecord;
import io.github.architers.server.file.mapper.FileTaskExportRecordMapper;
import io.github.architers.server.file.service.IFileTaskExportRecordService;
import org.springframework.stereotype.Service;

@Service
public class FileTaskExportRecordServiceImpl extends ServiceImpl<FileTaskExportRecordMapper, FileTaskExportRecord> implements IFileTaskExportRecordService {
    @Override
    public boolean updateByRequestId(FileTaskExportRecord fileTaskExportRecord) {
        Wrapper<FileTaskExportRecord> updateWrapper = Wrappers.lambdaUpdate(FileTaskExportRecord.class).eq(FileTaskExportRecord::getRequestId, fileTaskExportRecord.getRequestId());
        return this.baseMapper.update(fileTaskExportRecord, updateWrapper) == 1;
    }
}
