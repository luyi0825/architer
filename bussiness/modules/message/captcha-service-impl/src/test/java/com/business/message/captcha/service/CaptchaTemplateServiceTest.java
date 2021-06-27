package com.business.message.captcha.service;


import com.business.message.captcha.entity.CaptchaTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(value = SpringRunner.class)
public class CaptchaTemplateServiceTest {

    @Autowired
    private CaptchaTemplateService captchaTemplateService;

    /**
     * 添加验证码配置
     */
    @Test
   public void testAddCaptchaTemplate() {
        CaptchaTemplate captchaTemplate = new CaptchaTemplate();
        captchaTemplateService.addCaptchaTemplate(captchaTemplate);
    }

    /**
     * 通过主键id查询
     *
     * @param id 主键ID
     * @return 验证码模板
     */
    void testFindById(Integer id){

    }

    /**
     * 更新
     */
    void testUpdateCaptchaTemplate(){

    }

    /**
     * 通过验证码编码获取
     */
    void testFindByCode(){

    }

}