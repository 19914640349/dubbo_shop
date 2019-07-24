package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.aop.IsLogin;
import com.qf.arithmetic.PriceUtil;
import com.qf.entity.Address;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import com.qf.service.ICartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * FileName: OrderController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/24 19:22
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference
    private IAddressService addressService;

    @Reference
    private ICartService cartService;

    @RequestMapping("/toOrderEdit")
    @IsLogin(mustLogin = true)
    public String toOrderEdit(User user, Model model){

        // 拿到该用户的购物车
        List<ShopCart> cartList = cartService.queryCartList(user, null);

        // 计算总价
        BigDecimal totalPrice = PriceUtil.getTotalPrice(cartList);

        // 获取该用户的收货地址
        List<Address> addressList = addressService.queryAddressByUid(user.getId());

        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("addressList", addressList);
        return "orderEdit";
    }

    @RequestMapping("/addOrder")
    @IsLogin(mustLogin = true)
    public String addOrder(User user, Integer aid){
        System.out.println(user);
        System.out.println(aid);
        return "";
    }
}
