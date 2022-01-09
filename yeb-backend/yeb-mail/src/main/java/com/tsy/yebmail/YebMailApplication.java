package com.tsy.yebmail;

import com.tsy.yebserver.common.constant.MqConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author Steven.T
 * @date 2022/1/8
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class YebMailApplication {

    @Bean
    public Queue queue(){
        return new Queue(MqConstants.MAIL_QUEUE_NAME);
    }

    public static void main(String[] args) {
        SpringApplication.run(YebMailApplication.class, args);
    }
}
