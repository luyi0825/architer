package com.business.message.captcha.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author luyi
 */
@RequestMapping("/captchaApi")
@Api(tags = "验证码配置")
public interface CaptchaApi {
    /**
     * 生成图片验证码
     *
     * @param response 响应信息
     * @param session  用户回话信息
     * @param code     验证码配置编码
     */
    @ApiOperation(value = "生成验证码")
    @GetMapping("/produce/image/{code}")
    void produceImageCaptcha(HttpServletResponse response,
                             HttpSession session,
                             @PathVariable(name = "code") String code);

    /**
     * 生成邮件验证码
     *
     * @param code 验证码配置编码
     * @param mail 邮箱
     */
    @ApiOperation(value = "生成验证码")
    @GetMapping("/produce/mail/{code}/{mail}")
    void produceMailCaptcha(@PathVariable(name = "code") String code,
                            @PathVariable(name = "mail") String mail);
}
