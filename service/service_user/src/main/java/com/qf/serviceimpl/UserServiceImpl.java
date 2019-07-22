package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.password.BCryptUtil;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * FileName: UserServiceImpl.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/19 21:07
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @Override
    public int register(User user) {

        // 判断用户是否已存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        User user1 = userMapper.selectOne(queryWrapper);
        // 如果不为空，则说明用户已存在
        if (user1 != null) {
            return -1;
        }

        // 判断邮箱是否已被注册
        QueryWrapper queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("email", user.getEmail());
        User user2 = userMapper.selectOne(queryWrapper2);
        // 如果不为空，则说明邮箱已被注册
        if (user2 != null) {
            return -2;
        }

        // 对密码进行加密
        String hashPassword = BCryptUtil.hashPassword(user.getPassword());
        user.setPassword(hashPassword);
        // 进行注册并写入数据库
        return userMapper.insert(user);
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    @Override
    public User queryByUsername(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 重置密码
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public int resetPassword(String username, String password) {
        User user = queryByUsername(username);
        user.setPassword(BCryptUtil.hashPassword(password));
        return userMapper.updateById(user);
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {
        User user = queryByUsername(username);
        if (user != null) {
            // 判断密码是否正确
            boolean flag = BCryptUtil.checkPassword(password, user.getPassword());
            if (flag) {
                // 验证通过，则返回用户信息
                return user;
            }
        }
        return null;
    }
}
