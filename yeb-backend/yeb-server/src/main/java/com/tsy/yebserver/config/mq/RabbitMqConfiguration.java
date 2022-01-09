package com.tsy.yebserver.config.mq;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tsy.yebserver.common.constant.MqConstants;
import com.tsy.yebserver.dao.entity.MailLog;
import com.tsy.yebserver.service.IMailLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * @author Steven.T
 * @date 2022/1/9
 */
@Slf4j
@Configuration
public class RabbitMqConfiguration {

    @Bean
    public RabbitTemplate rabbitTemplate(@Autowired CachingConnectionFactory cachingConnectionFactory,
                                         @Autowired IMailLogService mailLogService) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        /*
         *  消息确认回调，确认消息是否到达broker
         *  correlationData:消息唯一标识
         *  ack:确认结果
         *  cause:失败原因
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            Assert.notNull(correlationData,"correlationData should be not null");
            final String msgId = correlationData.getId();
            if(ack){
                log.info("消息发送成功:{}",msgId);
                mailLogService.update(new LambdaUpdateWrapper<MailLog>().set(MailLog::getStatus,MqConstants.SUCCESS));
            }else {
                log.error("消息发送失败:{}",msgId);
            }
        });
        //消息失效回调，如router不到queue时会触发该回调
        rabbitTemplate.setReturnsCallback(returned -> {
            log.error("消息发送到queue过程中失败:{}",returned.getMessage().getBody());
        });
        return rabbitTemplate;
    }

    @Bean
    public Queue queue() {
        return new Queue(MqConstants.MAIL_QUEUE_NAME);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(MqConstants.MAIL_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(MqConstants.MAIL_ROUTING_KEY_NAME);
    }
}
