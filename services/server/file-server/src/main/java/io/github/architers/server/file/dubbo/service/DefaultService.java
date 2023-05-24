package io.github.architers.server.file.dubbo.service;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author luyi
 */
@DubboService(version = "1.0.0")
public class DefaultService implements DemoService{
    @Override
    public String sayName(String name) {
        return null;
    }
}
