package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * FileName: Address.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/24 19:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private String person;

    private String address;

    private String phone;

    private Integer isdefault = 0;

    private Date createtime;
}
