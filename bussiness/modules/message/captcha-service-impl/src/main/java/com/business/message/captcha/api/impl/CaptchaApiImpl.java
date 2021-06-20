package com.business.message.captcha.api.impl;

import com.business.message.captcha.api.CaptchaApi;
import com.business.message.captcha.component.CaptchaProducer;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author luyi
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
