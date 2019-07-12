package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * FileName: SearchController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/11 17:52
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    /**
     * 根据关键字查询商品
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping("/searchByKey")
    public String searchByKey(String keyword, Model model){
        List<Goods> goodsList = searchService.searchByKey(keyword);
        model.addAttribute("goodsList", goodsList);
        return "searchList";
    }

}
