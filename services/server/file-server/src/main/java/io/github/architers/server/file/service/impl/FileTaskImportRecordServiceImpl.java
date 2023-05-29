package io.github.architers.server.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.architers.component.mybatisplus.MybatisPageUtils;
import io.github.architers.context.query.PageRequest;
import io.github.architers.context.query.PageResult;
import io.github.architers.server.file.domain.entity.FileTaskImportRecord;
import io.github.architers.server.file.mapper.FileTaskImportRecordMapper;
import io.github.architers.server.file.domain.param.TaskRecordsPageParam;

import io.github.architers.server.file.service.IFileTaskImportRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class FileTaskImportRecordServiceImpl extends ServiceImpl<FileTaskImportRecordMapper,FileTaskImportRecord> implements IFileTaskImportRecordService {



    @Resource
    private FileTaskImportRecordMapper fileTaskImportRecordMapper;


    @Override
    public PageResult<FileTaskImportRecord> getImportRecordsByPage(PageRequest<TaskRecordsPageParam> pageRequest) {
        return MybatisPageUtils.pageQuery(pageRequest.getPageParam(), () -> {
            TaskRecordsPageParam taskRecordsPageParam = pageRequest.getRequestParam();
            Wrapper<FileTaskImportRecord> taskRecordWrapper = Wrappers.lambdaQuery(FileTaskImportRecord.class)
                    .eq(StringUtils.hasText(taskRecordsPageParam.getTaskCode()), FileTaskImportRecord::getTaskCode, taskRecordsPageParam.getTaskCode())
                    .orderByDesc(FileTaskImportRecord::getId);
            return fileTaskImportRecordMapper.selectList(taskRecordWrapper);
        });
    }
}
