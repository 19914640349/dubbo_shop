package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * FileName: OrderMapper.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/25 23:23
 */
public interface OrderMapper extends BaseMapper<Order> {

    int insertOrder(@Param("order") Order order, @Param("tableIndex") int tableIndex);

    List<Order> queryOrderByUid(@Param("uid")Integer uid, @Param("tableIndex") int tableIndex);

    int updateOrderStatus(@Param("orderId") String orderId, @Param("status") int status, @Param("tableIndex") int tableIndex);

    Order queryOrderByOid(@Param("orderId") String orderId, @Param("tableIndex") int tableIndex);
}
