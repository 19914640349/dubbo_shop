package com.qf.aop;

import java.lang.annotation.*;

/**
 * FileName: IsLogin.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/22 21:02
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLogin {

    // 是否必须登录
    boolean mustLogin() default false;

}
