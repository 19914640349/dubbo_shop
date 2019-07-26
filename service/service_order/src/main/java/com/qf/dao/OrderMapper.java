package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Order;
import org.apache.ibatis.annotations.Param;

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

}
