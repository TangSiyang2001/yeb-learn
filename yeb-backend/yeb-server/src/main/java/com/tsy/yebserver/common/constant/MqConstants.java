package com.tsy.yebserver.common.constant;

/**
 * 消息队列相关常量
 *
 * @author Steven.T
 * @date 2022/1/9
 */
public final class MqConstants {

    /**
     * 正在投递
     */
    public static final int DELIVERING = 0;

    /**
     * 投递成功
     */
    public static final int SUCCESS = 1;

    /**
     * 投递失败
     */
    public static final int FAIL = 2;

    /**
     * 最大尝试次数
     */
    public static final int MAX_ATTEMPTS = 3;

    /**
     * 重试间隔1min
     */
    public static final int INTERVAL = 60;

    /**
     * 邮件队列名
     */
    public static final String MAIL_QUEUE_NAME = "mail.queue";

    /**
     * 邮件交换机名
     */
    public static final String MAIL_EXCHANGE_NAME = "mail.exchange";

    /**
     * 邮件路由键名
     */
    public static final String MAIL_ROUTING_KEY_NAME = "mail.routing.key";
}
