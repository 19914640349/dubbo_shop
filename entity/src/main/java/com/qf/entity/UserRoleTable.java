package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * FileName: UserRoleTable.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/3 0:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleTable implements Serializable {

    private Integer uid;

    private Integer rid;

}
