package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * FileName: OrderDetailMapper.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/26 19:20
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    int insertOrderDetail(@Param("details") List<OrderDetail> details, @Param("tableIndex") int tableIndex);

}
