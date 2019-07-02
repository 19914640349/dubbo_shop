package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.BackUserMapper;
import com.qf.entity.BackUser;
import com.qf.service.IBackUserService;
import org.springframework.beans.factory.annotation.Autowired;

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

}
