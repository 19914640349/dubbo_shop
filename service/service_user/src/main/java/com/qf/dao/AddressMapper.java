package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Address;

/**
 * FileName: AddressMapper.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/24 21:14
 */
public interface AddressMapper extends BaseMapper<Address> {

    int insertAddress(Address address);

}
