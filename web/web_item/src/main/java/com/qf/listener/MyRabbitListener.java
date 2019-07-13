package com.qf.listener;

import com.qf.controller.ItemController;
import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * FileName: MyRabbitListener.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/13 18:00
 */
@Component
public class MyRabbitListener {

    @Autowired
    private Configuration configuration;

    @RabbitListener(queues = "item_queue")
    public void itemQueue(Goods goods){

        // 根据goodsItem.ftl生成商品静态页面
        try {
            Template template = configuration.getTemplate("goodsItem.ftl");

            Map<String, Object> map = new HashMap<>();
            map.put("goods", goods);
            map.put("images", goods.getGimage().split("\\|"));
            map.put("contextPath", "");

            // 获取生成模板的路径
            String path = ItemController.class.getResource("/static").getPath().replace("%20"," ");
            File file = new File(path + "/page");
            // 如果目录不存在则创建
            if (!file.exists()) {
                file.mkdirs();
            }
            try (
                    // 文件路径
                    Writer writer = new FileWriter(file.getAbsolutePath() + "/" + goods.getId() + ".html")
            ) {
                // 生成页面
                template.process(map, writer);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

    }

}
