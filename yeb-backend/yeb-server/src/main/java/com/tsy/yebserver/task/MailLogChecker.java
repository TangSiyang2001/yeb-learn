package com.tsy.yebserver.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tsy.yebserver.common.constant.MqConstants;
import com.tsy.yebserver.dao.entity.MailLog;
import com.tsy.yebserver.service.IMailLogService;
import com.tsy.yebserver.service.IMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Steven.T
 * @date 2022/1/9
 */
@Slf4j
@Component
public class MailLogChecker {
    @Resource
    private IMailLogService mailLogService;

    @Resource
    private IMqService mqService;

    @Scheduled(fixedRate = 10 * 1000)
    public void checkAndResend() {
        final List<MailLog> mailLogs = mailLogService.list(new LambdaQueryWrapper<MailLog>()
                .eq(MailLog::getStatus, MqConstants.DELIVERING)
                .lt(MailLog::getTryTime, LocalDateTime.now()));
        mailLogs.forEach(mailLog -> {
            if (mailLog.getCount() >= MqConstants.MAX_ATTEMPTS) {
                mailLogService.update(new LambdaUpdateWrapper<MailLog>()
                        .set(MailLog::getStatus, MqConstants.FAIL)
                        .eq(MailLog::getMsgId,mailLog.getMsgId()));
                return;
            }
            mqService.resendEmployeeMessage(mailLog);
        });
    }
}
