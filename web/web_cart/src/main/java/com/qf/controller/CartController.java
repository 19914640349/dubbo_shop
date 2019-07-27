package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qf.aop.IsLogin;
import com.qf.utils.PriceUtil;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import com.qf.service.ICartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * FileName: CartController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/22 20:56
 */
@Controller
@RequestMapping("cart")
public class CartController {

    @Reference
    private ICartService cartService;

    /**
     * 添加购物车，使用自定义注解判断是否登录
     *
     * @param cartToken
     * @param shopCart
     * @param user
     * @param response
     * @return
     */
    @RequestMapping("/addCart")
    @IsLogin
    @ResponseBody
    public String addCart(@CookieValue(name = "cartToken", required = false) String cartToken,
                          ShopCart shopCart, User user, String callback, HttpServletResponse response) {

        // 判断cookie是否有购物车，没有就创建
        if (cartToken == null) {
            cartToken = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("cartToken", cartToken);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        // 添加购物车
        cartService.addCart(shopCart, user, cartToken);

        return callback != null ? callback + "(" + JSON.toJSONString(shopCart) + ")" : JSON.toJSONString(shopCart);
    }

    /**
     * 查询购物车列表
     *
     * @param cartToken
     * @param user
     * @param callback
     * @return
     */
    @RequestMapping("/showCart")
    @IsLogin
    @ResponseBody
    public String showCart(@CookieValue(name = "cartToken", required = false) String cartToken, User user, String callback) {

        // 获得购物车列表
        List<ShopCart> cartList = cartService.queryCartList(user, cartToken);
        return callback != null ? callback + "(" + JSON.toJSONString(cartList) + ")" : JSON.toJSONString(cartList);
    }

    /**
     * 进入购物车页面
     *
     * @param cartToken
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/cartList")
    @IsLogin
    public String cartList(@CookieValue(name = "cartToken", required = false) String cartToken, User user, Model model) {

        // 获得购物车列表
        List<ShopCart> cartList = cartService.queryCartList(user, cartToken);

        // 计算总价
        BigDecimal totalPrice = PriceUtil.getTotalPrice(cartList);

        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPrice", totalPrice);

        return "cartList";
    }

}
