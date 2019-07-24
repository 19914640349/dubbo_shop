package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * FileName: ItemController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/13 9:13
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Reference
    private IGoodsService goodsService;

    @Autowired
    private Configuration configuration;

    /**
     * 生成商品静态页面
     * @param id
     */
    @RequestMapping("/createItem")
    @ResponseBody
    public void createItem(Integer id, HttpServletRequest request){

        // 根据id获得商品信息
        Goods goods = goodsService.queryGoodsById(id);

        // 根据goodsItem.ftl生成商品静态页面
        try {
            Template template = configuration.getTemplate("goodsItem.ftl");

            Map<String, Object> map = new HashMap<>();
            map.put("goods", goods);
            map.put("images", goods.getGimage().split("\\|"));
            map.put("contextPath", request.getContextPath());

            // 获取生成模板的路径
            String path = ItemController.class.getResource("/static").getPath().replace("%20"," ");
            File file = new File(path + "/page");
            // 如果目录不存在则创建
            if (!file.exists()) {
                file.mkdirs();
            }
            try (
                    // 文件路径
                    Writer writer = new FileWriter(file.getAbsolutePath() + "/" + id + ".html")
            ) {
                // 生成页面
                template.process(map, writer);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

}
