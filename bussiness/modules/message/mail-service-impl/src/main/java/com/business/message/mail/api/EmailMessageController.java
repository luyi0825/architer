package com.business.message.mail.api;


import com.business.message.mail.component.SendMessageProcess;
import com.business.message.mail.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邮件发送控制层
 *
 * @author ly
 */
@RestController
@RequestMapping("/email")
public class EmailMessageController {


    private SendMessageProcess sendMessageProcess;

    @Autowired
    public void setSendMessageProcess(SendMessageProcess sendMessageProcess) {
        this.sendMessageProcess = sendMessageProcess;
    }


    /**
     * 发送验证码
     *
     * @author luyi
     * @date 2020/8/15
     */
    @RequestMapping("/{templateCode}/send")
    public void sendVerificationCode(@RequestParam(name = "to") String to,
                                     @PathVariable(name = "templateCode") String templateCode) {
        Email email = new Email();
        email.setTemplateCode(templateCode);
        email.setReceivers(to);
        sendMessageProcess.send(email);
    }
}
