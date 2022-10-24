package io.github.architers.syscenter.user.service;

import io.github.architers.syscenter.user.domain.dto.LoginParamDTO;

/**
 * @author Administrator
 */
public interface TokenService {
    String loginByUserName(LoginParamDTO loginParamDTO);
}
