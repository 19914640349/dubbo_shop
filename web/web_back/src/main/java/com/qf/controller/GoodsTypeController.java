package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.GoodsType;
import com.qf.service.IGoodsTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * FileName: GoodsTypeController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/5 16:38
 */
@Controller
@RequestMapping("/goodsType")
public class GoodsTypeController {

    @Reference
    private IGoodsTypeService goodsTypeService;

    /**
     * 查询所有类别
     * @param model
     * @return
     */
    @RequestMapping("/goodsTypeList")
    public String goodsTypeList(Model model){
        List<GoodsType> goodsTypes = goodsTypeService.queryAllGoodsType();
        model.addAttribute("goodsTypes", goodsTypes);
        return "goodsTypeList";
    }

    /**
     * 添加商品类别
     * @return
     */
    @RequestMapping("/addGoodsType")
    public String addGoodsType(GoodsType goodsType){
        goodsTypeService.addGoodsType(goodsType);
        return "redirect:/goodsType/goodsTypeList";
    }

    /**
     * 查询所有类别返回json数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/ajaxGoodsTypeList")
    public List<GoodsType> ajaxGoodsTypeList(){
        return goodsTypeService.queryAllGoodsType();
    }

}
