package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * FileName: Email.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/19 0:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email implements Serializable {

    private String emailTo;

    private String topic;

    private String content;

}
