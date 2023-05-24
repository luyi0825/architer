package io.github.architers.server.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.architers.server.file.mapper.FileTaskMapper;
import io.github.architers.server.file.model.entity.FileTask;
import io.github.architers.server.file.service.IFileTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
public class FileTaskServiceImpl implements IFileTaskService {

    @Resource
    private FileTaskMapper fileTaskMapper;

    @Override
    public FileTask findByTaskCode(String taskCode) {
        Wrapper<FileTask> fileTaskWrapper = Wrappers.lambdaQuery(FileTask.class)
                .eq(FileTask::getTaskCode, taskCode);
        return fileTaskMapper.selectOne(fileTaskWrapper);
    }


}
