package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.AddressMapper;
import com.qf.entity.Address;
import com.qf.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * FileName: AddressServiceImpl.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/24 21:13
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 根据用户id获取收货地址
     * @param uid
     * @return
     */
    @Override
    public List<Address> queryAddressByUid(Integer uid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        return addressMapper.selectList(queryWrapper);
    }
}
