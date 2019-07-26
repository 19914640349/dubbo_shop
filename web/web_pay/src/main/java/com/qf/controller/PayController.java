package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Order;
import com.qf.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * FileName: PayController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/26 21:20
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Reference
    private IOrderService orderService;

    @RequestMapping("/aliPay")
    public String aliPay(String orderId, HttpServletResponse response){

        // 根据订单id查询到订单信息
        Order order = orderService.queryOrderByOid(orderId);

        return "";
    }

}
