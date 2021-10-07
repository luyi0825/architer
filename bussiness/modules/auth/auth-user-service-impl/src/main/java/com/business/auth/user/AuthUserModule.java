package com.business.auth.user;

import com.architecture.context.module.SubModule;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author luyi
 */
@SubModule(name = "authUserModule", caption = "权限用户模块", basePackages = "com.business.auth.user")
public class AuthUserModule {
}
