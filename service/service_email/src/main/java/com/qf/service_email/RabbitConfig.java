package com.qf.service_email;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FileName: RabbitConfig.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/19 20:14
 */
@Configuration
public class RabbitConfig {

    @Bean
    public org.springframework.amqp.core.Queue emailQueue(){
        return new Queue("email_queue");
    }

}
