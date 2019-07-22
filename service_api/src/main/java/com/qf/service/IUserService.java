package com.qf.service;

import com.qf.entity.User;

/**
 * FileName: IUserService.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/19 20:56
 */
public interface IUserService {

    int register(User user);

    User queryByUsername(String username);

    int resetPassword(String username, String password);

    User login(String username, String password);
}
