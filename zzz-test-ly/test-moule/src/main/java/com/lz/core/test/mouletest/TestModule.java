package com.lz.core.test.mouletest;

import com.lz.core.boot.Module;

/**
 * 测试模块，专门测试模块构建
 * @author luyi
 */

@Module(caption = "测试模块",name = "test-moule")
public class TestModule {
    public TestModule(){
        System.out.println("TestModule init");
    }
}
