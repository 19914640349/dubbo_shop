package com.qf.password;

import org.mindrot.jbcrypt.BCrypt;

/**
 * FileName: BCryptUtil.java
 * Desc: 对密码进行加密
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/19 21:22
 */
public class BCryptUtil {

    /**
     * 对密码进行加密
     * @param password
     * @return
     */
    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * 拿输入的密码跟数据库加密后的密码进行比较
     * @param password
     * @param hashPassword
     * @return
     */
    public static boolean checkPassword(String password, String hashPassword){
        return BCrypt.checkpw(password, hashPassword);
    }

}
