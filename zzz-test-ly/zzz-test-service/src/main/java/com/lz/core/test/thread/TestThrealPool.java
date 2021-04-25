package com.lz.core.test.thread;

import com.lz.thread.client.annotation.ThreadPoolClient;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


@Component
@ThreadPoolClient(name="test",caption = "测试")
public class TestThrealPool extends ThreadPoolTaskExecutor {

    @Override
    public void initialize() {
        super.initialize();
    }
}
