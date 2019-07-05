package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * FileName: GoodsController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/5 16:08
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodsService goodsService;

    /**
     * 查询所有所有商品信息
     * @param model
     * @return
     */
    @RequestMapping("/goodsList")
    public String goodsList(Model model){
        List<Goods> goodsList = goodsService.queryAllGoods();
        model.addAttribute("goodsList",goodsList);
        return "goodsList";
    }

    /**
     * 添加商品
     * @param goods
     * @return
     */
    @RequestMapping("/addGoods")
    public String addGoods(Goods goods){
        goodsService.addGoods(goods);
        return "redirect:/goods/goodsList";
    }

}
