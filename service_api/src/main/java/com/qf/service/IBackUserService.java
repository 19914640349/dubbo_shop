package com.qf.service;

import com.qf.entity.BackUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IBackUserService extends UserDetailsService {

    List<BackUser> queryAll();

    void addBackUser(BackUser backUser);

    void deleteBackUser(Integer id);

    void updateUserRoles(Integer uid, Integer[] rid);

    //BackUser login(String username, String password);
}
