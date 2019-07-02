package com.qf.service;

import com.qf.entity.Role;

import java.util.List;

public interface IRoleService {

    List<Role> queryAllRole();

    void addRole(Role role);

    List<Role> queryRolesByUid(Integer uid);
}
