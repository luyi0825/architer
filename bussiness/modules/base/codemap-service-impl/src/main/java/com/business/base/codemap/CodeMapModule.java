package com.business.base.codemap;

import com.architecture.context.common.SubModule;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author luyi
 */
@SubModule(name = "module-base-codeconvert", caption = "基础服务-代码集转换", basePackages = "com.business.base.codemap")
@MapperScan("com.business.base.codemap.dao")
public class CodeMapModule {
}
