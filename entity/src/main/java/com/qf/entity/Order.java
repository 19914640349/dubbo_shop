package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * FileName: Order.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/25 22:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    @TableId(type = IdType.INPUT)
    private String orderid;

    private Integer uid;

    private String person;

    private String address;

    private String phone;

    private BigDecimal allprice;

    private Date createtime;

    // 0：待付款   1：待发货   2：待收货   3：已收货
    private Integer status;

    @TableField(exist = false)
    private List<OrderDetail> orderDetails;

}
