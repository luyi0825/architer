package com.architecture.thread;

import org.springframework.stereotype.Component;

@Component
public class TestTaskExecutor extends BaseTaskExecutor {
    @Override
    public String getConfigId() {
        return "testTask";
    }
}
