package com.qf.service_goods;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FileName: RabbitMQConfig.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/13 17:11
 */
@Configuration
public class RabbitConfig {

    /**
     * 创建索引库队列
     * @return
     */
    @Bean(name = "searchQueue")
    public Queue getSearchQueue(){
        return new Queue("search_queue");
    }

    /**
     * 创建页面静态化队列
     * @return
     */
    @Bean(name = "itemQueue")
    public Queue getItemQueue(){
        return new Queue("item_queue");
    }

    /**
     * 创建商品服务交换机
     * @return
     */
    @Bean(name = "goodsExchange")
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange("goods_exchange");
    }

    /**
     * 把索引库队列与商品服务交换机进行绑定
     * @param searchQueue
     * @param goodsExchange
     * @return
     */
    @Bean
    public Binding getSearchBinding(Queue searchQueue, FanoutExchange goodsExchange){
        return BindingBuilder.bind(searchQueue).to(goodsExchange);
    }

    /**
     * 把页面静态化队列与商品服务交换机进行绑定
     * @param itemQueue
     * @param goodsExchange
     * @return
     */
    @Bean
    public Binding getItemBinding(Queue itemQueue, FanoutExchange goodsExchange){
        return BindingBuilder.bind(itemQueue).to(goodsExchange);
    }

}
