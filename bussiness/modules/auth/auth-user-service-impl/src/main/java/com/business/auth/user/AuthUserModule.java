package com.business.auth.user;

import com.architecture.ultimate.module.common.Module;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author luyi
 */
@Module(name = "authUserModule", caption = "权限用户模块", basePackages = "com.business.auth.user")
@MapperScan(basePackages = "com.business.auth.user.dao")
public class AuthUserModule {
}
