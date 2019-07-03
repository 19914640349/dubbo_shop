package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * FileName: RolePowerTable.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/3 15:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePowerTable implements Serializable {

    private Integer rid;

    private Integer pid;

}
