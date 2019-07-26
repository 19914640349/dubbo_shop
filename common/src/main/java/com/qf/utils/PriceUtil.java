package com.qf.utils;

import com.qf.entity.ShopCart;

import java.math.BigDecimal;
import java.util.List;

/**
 * FileName: TotalPriceUtil.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/24 20:26
 */
public class PriceUtil {

    /**
     * 计算购物车的总价
     * @param cartList
     * @return
     */
    public static BigDecimal getTotalPrice(List<ShopCart> cartList){
        // 计算总价
        BigDecimal totalPrice = BigDecimal.valueOf(0.0);
        if (cartList != null) {
            for (ShopCart shopCart : cartList) {
                totalPrice = totalPrice.add(shopCart.getSprice());
            }
        }
        return totalPrice;
    }

}
