package com.qf.web_item;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FileName: RabbitConfig.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/19 20:35
 */
@Configuration
public class RabbitConfig {

    @Bean(name = "itemQueue")
    public Queue getItemQueue(){
        return new Queue("item_queue");
    }

}
