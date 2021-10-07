package com.business.auth.user.api;

import com.business.auth.user.vo.LoginVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author luyi
 * 权限用户对外接口层
 */
@Controller
@RequestMapping("/authUser")
public interface AuthUserApi {
    @PostMapping("/login/{type}")
    String login(@PathVariable(name = "type") String type, @RequestBody LoginVO loginVO);
}
