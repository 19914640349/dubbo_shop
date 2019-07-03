package com.qf.service;

import com.qf.entity.BackUser;
import com.qf.entity.Role;

import java.util.List;

public interface IBackUserService {

    List<BackUser> queryAll();

    void addBackUser(BackUser backUser);

    void deleteBackUser(Integer id);

    void updateUserRoles(Integer uid, Integer[] rid);

    BackUser login(String username, String password);
}
