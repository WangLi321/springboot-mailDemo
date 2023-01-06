package com.example.springbootmaildemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author wangli
 * @Description
 * @date 2023/1/6 14:14
 */
@Service
public class MailUtils {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MailProperties mailProperties;

    @Resource
    private JavaMailSender javaMailSender;

    /**
     * 发送普通文本邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(mailProperties.getUsername());
        javaMailSender.send(message);
    }

    /**
     * 发送html邮件
     * @param to
     * @param subject
     * @param content
     * @throws MessagingException
     */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(mailProperties.getUsername());
        javaMailSender.send(message);
    }

    /**
     * 发送带附件邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     * @throws MessagingException
     */
    public void sendAttachmentMail(String to, String subject, String content, String filePath) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(mailProperties.getUsername());

        FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
        String fileName = fileSystemResource.getFilename();
        helper.addAttachment(fileName, fileSystemResource);
        //添加多个附件可以继续helper.addAttachment(fileName, fileSystemResource)，通常是遍历filePath集合
        javaMailSender.send(message);
    }

    /**
     * 发送静态邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    public void sendPicMail(String to, String subject, String content, String rscPath, String rscId) {
        logger.info("发送静态邮件开始：{},{},{},{},{}", to, subject, content, rscPath, rscId);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setFrom(mailProperties.getUsername());

            FileSystemResource fileSystemResource = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, fileSystemResource);
            javaMailSender.send(message);
            logger.info("发送邮件成功");
        } catch (MessagingException e) {
            logger.error("发送图片邮件失败", e);
        }
    }


}
