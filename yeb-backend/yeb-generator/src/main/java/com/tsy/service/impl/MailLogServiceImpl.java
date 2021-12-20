package com.tsy.service.impl;

import com.tsy.entity.MailLog;
import com.tsy.mapper.MailLogMapper;
import com.tsy.service.IMailLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Steven
 * @since 2021-11-13
 */
@Service
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
