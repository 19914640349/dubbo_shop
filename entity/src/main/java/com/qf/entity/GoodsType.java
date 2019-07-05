package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * FileName: GoodsType.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/5 15:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("goodstype")
public class GoodsType implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String tname;

    private Integer pid;

    @TableField(value = "createtime")
    private Date createTime;

    private Integer status;

    @TableField(exist = false)
    private String pname;
}
