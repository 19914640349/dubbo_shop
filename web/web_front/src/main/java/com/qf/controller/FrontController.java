package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.entity.GoodsType;
import com.qf.service.IGoodsService;
import com.qf.service.IGoodsTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * FileName: FrontController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/14 13:09
 */
@Controller
@RequestMapping("/front")
public class FrontController {

    @Reference
    private IGoodsTypeService goodsTypeService;

    @Reference
    private IGoodsService goodsService;

    /**
     * 跳转到首页
     * @param model
     * @return
     */
    @RequestMapping("/toHome")
    public String toHome(Model model){
        List<GoodsType> goodsTypeList = goodsTypeService.queryAllGoodsType();
        List<Goods> goodsList = goodsService.queryAllGoods();
        model.addAttribute("goodsTypeList", goodsTypeList);
        model.addAttribute("goodsList", goodsList);
        return "home";
    }

}
