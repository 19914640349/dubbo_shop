package com.qf.service_search;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FileName: RabbitConfig.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/19 20:33
 */
@Configuration
public class RabbitConfig {

    @Bean(name = "searchQueue")
    public Queue getSearchQueue(){
        return new Queue("search_queue");
    }

}
