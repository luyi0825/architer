package io.github.architers.thread;

import io.github.architers.thread.BaseTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class TestTaskExecutor extends BaseTaskExecutor {
    @Override
    public String getConfigId() {
        return "testTask";
    }
}
