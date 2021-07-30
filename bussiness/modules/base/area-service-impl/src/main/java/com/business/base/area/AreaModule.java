package com.business.base.area;

import com.architecture.ultimate.module.common.SubModule;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author luyi
 */
@SubModule(name = "module-base-area", caption = "基础数据区划模块",basePackages = " com.business.base.area")
@MapperScan("com.business.base.area.dao")
public class AreaModule {
}
