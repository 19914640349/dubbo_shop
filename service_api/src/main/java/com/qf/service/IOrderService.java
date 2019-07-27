package com.qf.service;

import com.qf.entity.Order;
import com.qf.entity.User;

import java.util.List;

/**
 * FileName: IOrderService.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/25 22:59
 */
public interface IOrderService {

    Order addOrderByUid(User user, Integer aid);

    Order queryOrderByOid(String orderId);

    List<Order> queryOrderByUid(Integer uid);

    int updateOrderStatus(String orderId, int status);
}
