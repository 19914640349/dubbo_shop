package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * FileName: OrderDetils.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/25 22:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order_detail")
public class OrderDetail implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String orderid;

    private Integer gid;

    private String gname;

    private BigDecimal gprice;

    private String gimage;

    private Integer gnumber;

    private BigDecimal sprice;

}
