package com.tsy.yebserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tsy.yebserver.common.constant.MqConstants;
import com.tsy.yebserver.dao.entity.Employee;
import com.tsy.yebserver.dao.entity.MailLog;
import com.tsy.yebserver.service.IEmployeeService;
import com.tsy.yebserver.service.IMailLogService;
import com.tsy.yebserver.service.IMqService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Steven.T
 * @date 2022/1/9
 */
@Service
public class MqServiceImpl implements IMqService {

    @Resource
    private IMailLogService mailLogService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private IEmployeeService employeeService;

    @Override
    public void sendEmployeeMessage(@NotNull Employee employee) {
        final String msgId = recordMailLog(employee.getId());
        rabbitTemplate.convertAndSend(
                MqConstants.MAIL_EXCHANGE_NAME,
                MqConstants.MAIL_ROUTING_KEY_NAME,
                employee,
                new CorrelationData(msgId)
        );

    }

    private String recordMailLog(int eid) {
        final String msgId = UUID.randomUUID().toString();
        MailLog mailLog = new MailLog();
        mailLog.setMsgId(msgId);
        mailLog.setEid(eid);
        mailLog.setCount(0);
        mailLog.setCreateTime(LocalDateTime.now());
        mailLog.setTryTime(LocalDateTime.now().plusSeconds(MqConstants.INTERVAL));
        mailLog.setUpdateTime(LocalDateTime.now());
        mailLog.setExchange(MqConstants.MAIL_EXCHANGE_NAME);
        mailLog.setRouteKey(MqConstants.MAIL_ROUTING_KEY_NAME);
        mailLog.setStatus(MqConstants.DELIVERING);
        mailLogService.save(mailLog);
        return msgId;
    }

    @Override
    public void resendEmployeeMessage(MailLog mailLog) {
        mailLogService.update(
                new LambdaUpdateWrapper<MailLog>()
                        .set(MailLog::getCount, mailLog.getCount() + 1)
                        .set(MailLog::getUpdateTime, LocalDateTime.now())
                        .set(MailLog::getTryTime, LocalDateTime.now().plusSeconds(MqConstants.INTERVAL))
                        .eq(MailLog::getMsgId,mailLog.getMsgId())
        );
        Employee employee=employeeService.getEmployeeById(mailLog.getEid()).get(0);
        rabbitTemplate.convertAndSend(
                MqConstants.MAIL_EXCHANGE_NAME,
                MqConstants.MAIL_ROUTING_KEY_NAME,
                employee,
                new CorrelationData(mailLog.getMsgId())
        );
    }
}
