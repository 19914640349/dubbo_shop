package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.BackUserMapper;
import com.qf.dao.UserRoleTableMapper;
import com.qf.entity.BackUser;
import com.qf.entity.UserRoleTable;
import com.qf.service.IBackUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * FileName: BackUserServiceImpl.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/1 20:26
 */
@Service
public class BackUserServiceImpl implements IBackUserService {

    @Autowired
    private BackUserMapper backUserMapper;

    @Autowired
    private UserRoleTableMapper userRoleTableMapper;

    /**
     * 查询所有后台职工信息
     * @return
     */
    @Override
    public List<BackUser> queryAll() {
        return backUserMapper.selectList(null);
    }

    /**
     * 添加后台用户
     * @param backUser
     * @return
     */
    @Override
    public void addBackUser(BackUser backUser) {
        backUserMapper.insert(backUser);
    }

    /**
     * 删除后台用户
     * @param id
     */
    @Override
    public void deleteBackUser(Integer id) {
        backUserMapper.deleteById(id);
    }

    /**
     * 修改员工角色
     * @param uid
     * @param rid
     */
    @Override
    @Transactional
    public void updateUserRoles(Integer uid, Integer[] rid) {
        //根据用户id删除用户和角色的所有关系
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid",uid);
        userRoleTableMapper.delete(queryWrapper);

        //将重新选择的角色保存
        for (Integer roleid : rid) {
            UserRoleTable userRoleTable = new UserRoleTable(uid,roleid);
            userRoleTableMapper.insert(userRoleTable);
        }
    }

    @Override
    public BackUser login(String username, String password) {
        BackUser backUser = backUserMapper.queryByUsername(username);
        if (backUser != null && backUser.getPassword().equals(password)) {
            return backUser;
        }
        return null;
    }

}
