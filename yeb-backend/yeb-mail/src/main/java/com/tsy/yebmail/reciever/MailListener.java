package com.tsy.yebmail.reciever;

import com.tsy.yebserver.dao.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author Steven.T
 * @date 2022/1/8
 */
@Component
public class MailListener {

    private static final Logger LOGGER= LoggerFactory.getLogger(MailListener.class);

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private MailProperties mailProperties;

    @Resource
    private TemplateEngine templateEngine;

    @RabbitListener(queues = "mail.welcome")
    public void handle(Employee employee){
        //Mime为媒体类型，表示该消息是以某种格式（html）发送的
        final MimeMessage message = mailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(employee.getEmail());
            helper.setSubject("欢迎");
            helper.setSentDate(new Date());
            helper.setText(writeEmail(employee),true);
            mailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error("邮件发送失败:{}",e.getMessage());
        }
    }

    private String writeEmail(Employee employee) {
        final Context context = new Context();
        context.setVariable("name",employee.getName());
        context.setVariable("posName",employee.getPosition().getName());
        context.setVariable("jobLevelName",employee.getJobLevel().getName());
        context.setVariable("departmentName",employee.getDepartment().getName());
        return templateEngine.process("mail",context);
    }
}
