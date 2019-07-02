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
 * FileName: Role.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/2 0:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "rolename")
    private String roleName;

    @TableField(value = "rolealias")
    private String roleAlias;

    @TableField(value = "createtime")
    private Date createTime;

    private Integer status;

}
