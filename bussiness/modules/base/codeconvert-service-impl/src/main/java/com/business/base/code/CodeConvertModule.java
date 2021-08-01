package com.business.base.code;

import com.architecture.ultimate.module.common.SubModule;
import org.mybatis.spring.annotation.MapperScan;

@SubModule(name = "module-base-codeconvert", caption = "基础服务-代码集转换", basePackages = "com.business.base.code")
@MapperScan("com.business.base.code.dao")
public class CodeConvertModule {
}
