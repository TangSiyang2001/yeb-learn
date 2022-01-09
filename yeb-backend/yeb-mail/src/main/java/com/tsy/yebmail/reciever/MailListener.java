package com.tsy.yebmail.reciever;

import com.rabbitmq.client.Channel;
import com.tsy.yebserver.common.constant.MqConstants;
import com.tsy.yebserver.dao.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Steven.T
 * @date 2022/1/8
 */
@Component
public class MailListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailListener.class);

    private static final String MAIL_LOG_PREFIX = "mail_log_";

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private MailProperties mailProperties;

    @Resource
    private TemplateEngine templateEngine;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 监听邮件发送消息，考虑到yebserver中的定时任务和添加员工服务可能重复发送消息，利用redis实现消费端幂等性
     * @param message 消息
     * @param channel 信道
     */
    @RabbitListener(queues = MqConstants.MAIL_QUEUE_NAME)
    public void handle(@NotNull Message<Employee> message, @NotNull Channel channel) {
        final Employee employee = message.getPayload();
        final MessageHeaders headers = message.getHeaders();
        //消息序号
        final Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //消息id
        final String msgId = (String) headers.get(PublisherCallbackChannel.RETURNED_MESSAGE_CORRELATION_KEY);
        final ValueOperations<String, Object> objectValueOperations = redisTemplate.opsForValue();
        //redis存储mail_log的键
        final String redisKey = MAIL_LOG_PREFIX + msgId;
        Assert.notNull(tag, "tag should not be null");
        Assert.notNull(msgId, "message id should not be null");
        try {
            //如果已经存在该消息，则不再发送重复消息
            if (objectValueOperations.get(redisKey) != null) {
                //手动确认接受成功
                channel.basicAck(tag, false);
                return;
            }
            sendEmailTo(employee);
            objectValueOperations.set(redisKey, msgId, 40, TimeUnit.SECONDS);
            channel.basicAck(tag, false);
        } catch (MessagingException | IOException e) {
            LOGGER.error("邮件发送失败:{}", e.getMessage());
            try {
                //手动确认接受失败
                channel.basicNack(tag, false, true);
            } catch (IOException ignored) {
                // ignore
            }
        }
    }

    private void sendEmailTo(@NotNull Employee employee) throws MessagingException {
        //Mime为媒体类型，表示该消息是以某种格式（html）发送的
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(mailProperties.getUsername());
        helper.setTo(employee.getEmail());
        helper.setSubject("欢迎");
        helper.setSentDate(new Date());
        helper.setText(createText(employee), true);
        mailSender.send(mimeMessage);
    }

    private String createText(@NotNull Employee employee) {
        final Context context = new Context();
        context.setVariable("name", employee.getName());
        context.setVariable("posName", employee.getPosition().getName());
        context.setVariable("jobLevelName", employee.getJobLevel().getName());
        context.setVariable("departmentName", employee.getDepartment().getName());
        return templateEngine.process("mail", context);
    }
}
