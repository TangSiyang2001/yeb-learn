package com.tsy.yebserver.service;

import com.tsy.yebserver.dao.entity.Employee;
import com.tsy.yebserver.dao.entity.MailLog;

/**
 * @author Steven.T
 * @date 2022/1/9
 */
public interface IMqService {
    /**
     * 将员工信息发送到消息队列
     * @param employee 员工
     */
    void sendEmployeeMessage(Employee employee);

    /**
     * 重发员工信息
     * @param mailLog 邮件日志
     */
    void resendEmployeeMessage(MailLog mailLog);
}
