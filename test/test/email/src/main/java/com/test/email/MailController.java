package com.test.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Resource
    private MailSenderSelector mailSenderSelector;

    @PostMapping("/send")
    public void send() {
        for (int i = 0; i < 300; i++) {
            MailSender mailSender = mailSenderSelector.selectHealthyOne("qq");
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(mailSender.getUsername());
            //  simpleMailMessage.setSentDate();
            simpleMailMessage.setTo("893431435@qq.com");
            simpleMailMessage.setSubject("爱你");
            simpleMailMessage.setText("记住阿爸爱你哟");
            mailSender.send(simpleMailMessage);
        }

    }
}
