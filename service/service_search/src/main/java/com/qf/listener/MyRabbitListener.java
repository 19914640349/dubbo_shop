package com.qf.listener;

import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FileName: MyRabbitListener.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/13 17:52
 */
@Component
public class MyRabbitListener {

    @Autowired
    private ISearchService searchService;

    @RabbitListener(queues = "search_queue")
    public void SearchQueue(Goods goods){
        searchService.addGoods(goods);
    }

}
