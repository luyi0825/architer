package io.github.architers.center.menu.api;

import io.github.architers.center.menu.domain.dto.LoginParamDTO;
import io.github.architers.center.menu.service.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * token对应的接口
 *
 * @author luyi
 */
@RestController
@RequestMapping("/public/api/token")
public class TokenApi {


    @Resource
    private TokenService tokenService;

    /**
     * 通过验签获取token
     */


    /**
     * 登录获取token
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginParamDTO loginParamDTO) {
       return tokenService.loginByUserName(loginParamDTO);
    }

}
