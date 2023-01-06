package com.example.springbootmaildemo.controller;

import com.example.springbootmaildemo.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;

/**
 * @author wangli
 * @Description
 * @date 2023/1/6 14:13
 */
@RestController
@RequestMapping("/mailtest")
public class MailController {
    public static final String toEmail = "XXXXXXXX@163.com";
    @Autowired
    private MailUtils mailUtils;
    @Resource
    private TemplateEngine templateEngine;

    @GetMapping("sendSimpleMail")
    public String sendSimpleMail() {
        mailUtils.sendSimpleMail(toEmail, "这是第一封邮件Test", "大家好，这是我的第一封邮件");
        return "sendSimpleMail发送完成";
    }

    @GetMapping("sendHtmlMail")
    public String  sendHtmlMail() throws MessagingException {
        String content = "<html>\n" + "<body>\n" + "<h3>这是第一封Html邮件</h3>\n" + "</body>\n" + "</html>";
        mailUtils.sendHtmlMail(toEmail, "这是第一封Html邮件test", content);
        return "sendHtmlMail发送完成";
    }

    @GetMapping("sendAttachmentMail")
    public String sendAttachmentMail() throws MessagingException {
        String filePath = "C:\\Users\\wl\\Saved Games\\Downloads\\提高北斗RTK定位精度的方法_黄雪琪.caj";
        mailUtils.sendAttachmentMail(toEmail, "这是第一封带附件邮件test", "这是第一封带附件邮件", filePath);
        return "sendAttachmentMail发送完成";
    }

    @GetMapping("sendPicMail")
    public String sendPicMail() {
        String rscPath = "C:\\Users\\wl\\Pictures\\头像.jpg";
        String rscId = "pic001";
        //多个邮件就加多个img标签
        String content = "<html><body>\n这是第一封图片邮件:<img src=\'cid:" + rscId + "\'></body></html>";
        System.out.println(content);
        mailUtils.sendPicMail(toEmail, "这是第一封图片邮件test", content, rscPath, rscId);
        return "sendPicMail发送完成";
    }

    @GetMapping("sendTemplatesMail")
    public String sendTemplatesMail() throws MessagingException {
        Context context = new Context();
        context.setVariable("id", "6844904153047646216");
        String emailContext = templateEngine.process("emailTemplate", context);
        mailUtils.sendHtmlMail(toEmail, "这是一个模板邮件test", emailContext);
        return "sendTemplatesMail发送完成";
    }
}
