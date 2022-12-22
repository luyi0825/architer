package io.github.architers.server.file.service.impl;

import io.github.architers.server.file.service.TestService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Override
    @Async
    public void test() {
        System.out.println("1");
    }
}
