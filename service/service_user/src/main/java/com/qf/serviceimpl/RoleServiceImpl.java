package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.RoleMapper;
import com.qf.dao.RolePowerTableMapper;
import com.qf.dao.UserRoleTableMapper;
import com.qf.entity.Role;
import com.qf.entity.RolePowerTable;
import com.qf.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private UserRoleTableMapper userRoleTableMapper;

    @Autowired
    private RolePowerTableMapper rolePowerTableMapper;

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
     * @param role  角色对象
     */
    @Override
    public void addRole(Role role) {
        roleMapper.insert(role);
    }

    /**
     * 根据用户的id查询所有角色并拥有哪些
     * @param uid   用户id
     * @return
     */
    @Override
    public List<Role> queryRolesByUid(Integer uid) {
        return roleMapper.queryRolesByUid(uid);
    }

    /**
     * 修改角色的权限
     * @param rid   角色id
     * @param pids  权限id数组
     */
    @Override
    @Transactional
    public void updateRolePower(Integer rid, Integer[] pids) {
        // 删除此角色的所有权限
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("rid",rid);
        rolePowerTableMapper.delete(queryWrapper);
        // 给此角色添加重新选择的权限
        for (Integer pid : pids) {
            RolePowerTable rolePowerTable = new RolePowerTable(rid,pid);
            rolePowerTableMapper.insert(rolePowerTable);
        }
    }

    /**
     * 删除角色
     * @param id
     */
    @Override
    @Transactional
    public void deleteRole(Integer id) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("rid",id);
        // 删除此角色的所有关联用户
        userRoleTableMapper.delete(queryWrapper);
        // 删除此角色的所有关联权限
        rolePowerTableMapper.delete(queryWrapper);

        roleMapper.deleteById(id);
    }
}
