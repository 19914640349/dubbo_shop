package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.PowerMapper;
import com.qf.entity.Power;
import com.qf.service.IPowerService;
import org.springframework.beans.factory.annotation.Autowired;

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
}
