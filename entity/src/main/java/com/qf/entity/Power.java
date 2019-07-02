package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * FileName: Power.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/2 16:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Power implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 父权限id
    private Integer pid;

    @TableField(value = "powername")
    private String powerName;

    @TableField(value = "powerpath")
    private String powerPath;

    @TableField(value = "createtime")
    private Date createTime;

    private Integer status;

    @TableField(exist = false, value = "parentname")
    private String parentName;

}
