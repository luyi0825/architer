package io.github.architers.center.menu.service;

import io.github.architers.center.menu.domain.dto.LoginParamDTO;

/**
 * @author Administrator
 */
public interface TokenService {
    String loginByUserName(LoginParamDTO loginParamDTO);
}
