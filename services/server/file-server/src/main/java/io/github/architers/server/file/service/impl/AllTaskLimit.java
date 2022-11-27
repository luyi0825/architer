package io.github.architers.server.file.service.impl;

import io.github.architers.server.file.domain.entity.FileTaskConfig;
import io.github.architers.server.file.domain.dto.ExecuteTaskParam;
import io.github.architers.server.file.service.ITaskLimit;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AllTaskLimit implements ITaskLimit {

    @Resource
    private RedissonClient redisson;



    @Override
    public boolean support(Integer target) {
        return false;
    }

    @Override
    public boolean canExecute(ExecuteTaskParam executeTaskParam, FileTaskConfig taskLimit ) {

        long count = redisson.getAtomicLong("taskLimit:all:" + executeTaskParam.getTaskCode()).get();
        return count < taskLimit.getLimitCount();
    }

    @Override
    public void startExecute(ExecuteTaskParam executeTaskParam) {
         redisson.getAtomicLong("taskLimit:all:" + executeTaskParam.getTaskCode()).incrementAndGet();
    }

    public void endExecute(ExecuteTaskParam executeTaskParam){
        redisson.getAtomicLong("taskLimit:all:" + executeTaskParam.getTaskCode()).incrementAndGet();
    }
}
