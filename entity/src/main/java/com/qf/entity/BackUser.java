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
import java.util.List;

/**
 * FileName: BackUserServiceImpl.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/1 20:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("back_user")
public class BackUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String name;

    private Integer sex;

    @TableField(value = "createtime")
    private Date createTime;

    private Integer status;

    @TableField(exist = false)
    private List<Role> roles;

    @TableField(exist = false)
    private List<Power> powers;

}
