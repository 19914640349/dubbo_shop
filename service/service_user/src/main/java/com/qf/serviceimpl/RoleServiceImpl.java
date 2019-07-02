package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.RoleMapper;
import com.qf.entity.Role;
import com.qf.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * FileName: RoleServiceImpl.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/2 10:15
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public List<Role> queryAllRole() {
        return roleMapper.selectList(null);
    }

    /**
     * 添加角色
     * @param role
     */
    @Override
    public void addRole(Role role) {
        roleMapper.insert(role);
    }

    /**
     * 根据用户的id查询所有角色并拥有哪些
     * @param uid
     * @return
     */
    @Override
    public List<Role> queryRolesByUid(Integer uid) {
        return roleMapper.queryRolesByUid(uid);
    }
}
