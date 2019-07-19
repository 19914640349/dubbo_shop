package com.qf.web_sso;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FileName: RabbitConfig.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/19 20:01
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue emailQueue(){
        return new Queue("email_queue");
    }

    @Bean
    public FanoutExchange emailExchange(){
        return new FanoutExchange("email_exchange");
    }

    @Bean
    public Binding getBinding(Queue emailQueue, FanoutExchange emailExchange){
        return BindingBuilder.bind(emailQueue).to(emailExchange);
    }

}
