package com.qf.service;

import com.qf.entity.Address;

import java.util.List;

/**
 * FileName: IAddressService.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/24 21:09
 */
public interface IAddressService {

    List<Address> queryAddressByUid(Integer uid);

    int insertAddress(Address address);

    Address queryByAid(Integer aid);
}
