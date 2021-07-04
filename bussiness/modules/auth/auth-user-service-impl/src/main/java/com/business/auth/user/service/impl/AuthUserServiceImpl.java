package com.business.auth.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.auth.user.entity.AuthUser;
import com.business.auth.user.service.AuthUserService;
import com.core.mybatisplus.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 权限用户service的实现类
 *
 * @author luyi
 */
@Service
public class AuthUserServiceImpl extends BaseServiceImpl<AuthUser> implements AuthUserService {

}
