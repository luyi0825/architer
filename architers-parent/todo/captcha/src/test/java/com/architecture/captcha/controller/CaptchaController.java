package io.github.architers.captcha.controller;


import io.github.architers.captcha.CharacterGifCaptcha;
import io.github.architers.captcha.CharacterStaticCaptcha;
import io.github.architers.captcha.base.Captcha;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author luyi
 * 验证码控制层测试
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {

    public CaptchaController() {
        System.out.println("CaptchaController");
    }

    @RequestMapping("/test/{type}")
    public void test(@PathVariable(name = "type") String type, HttpServletResponse response) throws IOException {
        Captcha captcha = null;
        if (type.equals("static")) {
            captcha = new CharacterStaticCaptcha();
        } else if (type.equals("gif")) {
            captcha = new CharacterGifCaptcha();
        }
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        captcha.out(response.getOutputStream(), null);
    }
}
