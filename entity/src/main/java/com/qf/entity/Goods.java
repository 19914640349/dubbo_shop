package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * FileName: Goods.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/5 15:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String gname;

    private String ginfo;

    private String gimage;

    private BigDecimal gprice;

    private Integer gsave;

    private Integer tid;

    @TableField(exist = false)
    private GoodsType goodsType;

}
