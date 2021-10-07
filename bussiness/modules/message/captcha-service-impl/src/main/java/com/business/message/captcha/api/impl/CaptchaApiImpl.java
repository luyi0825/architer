package com.business.message.captcha.api.impl;


import com.business.message.captcha.api.CaptchaApi;
import com.business.message.captcha.component.CaptchaProducer;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author luyi
 * 验证码接口实现层
 */
@RestController
public class CaptchaApiImpl implements CaptchaApi {
    private final CaptchaProducer captchaProducer;


    public CaptchaApiImpl(CaptchaProducer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    @Override
    public void produceImageCaptcha(HttpServletResponse response,
                                    HttpSession session,
                                    String captchaCode) {
        captchaProducer.produceImage(response, session, captchaCode);
    }

    @Override
    public void produceMailCaptcha(String code, String mail) {
        captchaProducer.produceMail(code, mail);
    }


}
