package io.github.architers.server.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.architers.server.file.dao.FileTaskDao;
import io.github.architers.server.file.domain.entity.FileTask;
import io.github.architers.server.file.service.IFileTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Service
public class FileFileTaskServiceImpl implements IFileTaskService {

    @Resource
    private FileTaskDao fileTaskDao;

    @Override
    public FileTask findByTaskCode(String taskCode) {
        Wrapper<FileTask> fileTaskWrapper = Wrappers.lambdaQuery(FileTask.class)
                .eq(FileTask::getTaskCode, taskCode);
        return fileTaskDao.selectOne(fileTaskWrapper);
    }
}
