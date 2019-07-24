package com.qf.service;

import com.qf.entity.ShopCart;
import com.qf.entity.User;

import java.util.List;

/**
 * FileName: ShopCartService.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/22 23:55
 */
public interface ICartService {

    int addCart(ShopCart shopCart, User user, String cartToken);

    List<ShopCart> queryCartList(User user, String cartToken);

    int mergeCart(String cartToken, User user);
}
