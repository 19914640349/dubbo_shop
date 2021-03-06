package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.PowerMapper;
import com.qf.dao.RolePowerTableMapper;
import com.qf.entity.Power;
import com.qf.service.IPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * FileName: PowerServiceImpl.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/2 16:28
 */
@Service
public class PowerServiceImpl implements IPowerService {

    @Autowired
    private PowerMapper powerMapper;

    @Autowired
    private RolePowerTableMapper rolePowerTableMapper;

    /**
     * 查询所有权限
     * @return
     */
    @Override
    public List<Power> queryAllPower() {
        return powerMapper.queryAllPower();
    }

    /**
     * 添加权限
     * @param power
     */
    @Override
    public void addPower(Power power) {
        powerMapper.insert(power);
    }

    /**
     * 根据角色id查询所有的权限
     * @param rid
     * @return
     */
    @Override
    public List<Power> queryPowersByRid(Integer rid) {
        return powerMapper.queryPowersByRid(rid);
    }

    /**
     * 删除权限
     * @param id
     */
    @Override
    @Transactional
    public void deletePower(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("pid",id);
        // 删除此权限的所有关联角色
        rolePowerTableMapper.delete(queryWrapper);
    }
}
