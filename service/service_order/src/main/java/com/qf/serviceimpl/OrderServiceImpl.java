package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.OrderDetailMapper;
import com.qf.dao.OrderMapper;
import com.qf.dataconfig.DynamicDataSource;
import com.qf.entity.*;
import com.qf.service.IAddressService;
import com.qf.service.ICartService;
import com.qf.service.IOrderService;
import com.qf.utils.OrderUtil;
import com.qf.utils.PriceUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * FileName: OrderServiceImpl.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/25 23:20
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderUtil orderUtil;

    @Reference
    private IAddressService addressService;

    @Reference
    private ICartService cartService;

    /**
     * 添加订单
     *
     * @param user
     * @param aid
     * @return
     */
    @Override
    public Order addOrderByUid(User user, Integer aid) {

        // 获取用户id后4位
        int uid = Integer.parseInt(orderUtil.getUId(user.getId()));

        // 获取订单id
        String orderId = orderUtil.createOrderIdByUid(uid);

        // 通过用户id后4位，确认数据库
        int dbIndex = uid % 2 + 1;
        DynamicDataSource.set("orderdb" + dbIndex);

        // 通过用户id后4位，确认表
        int tableIndex = uid % 2 / 2 + 1;

        // 通过地址id获取订单的详细收货地址
        Address address = addressService.queryByAid(aid);

        // 计算出购物车的总价
        List<ShopCart> cartList = cartService.queryCartList(user, null);
        BigDecimal totalPrice = PriceUtil.getTotalPrice(cartList);

        // 创建一个订单
        Order order = new Order();
        order.setOrderid(orderId);
        order.setUid(uid);
        order.setPerson(address.getPerson());
        order.setAddress(address.getAddress());
        order.setPhone(address.getPhone());
        order.setAllprice(totalPrice);
        order.setCreatetime(new Date());
        order.setStatus(0);

        // 添加订单到数据库
        orderMapper.insertOrder(order, tableIndex);

        // 创建订单详情
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ShopCart shopCart : cartList) {

            // 生成每个订单详情
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderid(orderId);
            orderDetail.setGid(shopCart.getGid());
            orderDetail.setGname(shopCart.getGoods().getGname());
            orderDetail.setGprice(shopCart.getGoods().getGprice());
            orderDetail.setGimage(shopCart.getGoods().getGimage());
            orderDetail.setGnumber(shopCart.getGnumber());
            orderDetail.setSprice(shopCart.getSprice());

            // 添加到集合中
            orderDetailList.add(orderDetail);

            // 批量插入订单详情
            if(orderDetailList.size() % 1000 == 0 || orderDetailList.size() == cartList.size()){
                orderDetailMapper.insertOrderDetail(orderDetailList, tableIndex);
                orderDetailList.clear();
            }
        }

        // 清空该用户的购物车
        cartService.clearCartByUid(user.getId());

        return order;
    }

    /**
     * 根据订单id查询详细订单
     * @param orderId
     * @return
     */
    @Override
    public Order queryOrderByOid(String orderId) {

        // 根据订单id查到数据库
        Integer uid = orderUtil.getUidByOid(orderId);

        // 通过用户id后4位，确认数据库
        int dbIndex = uid % 2 + 1;
        DynamicDataSource.set("orderdb" + dbIndex);

        // 通过用户id后4位，确认表
        int tableIndex = uid % 2 / 2 + 1;
        return null;
    }
}
