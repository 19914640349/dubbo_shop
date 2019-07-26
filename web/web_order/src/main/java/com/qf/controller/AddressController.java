package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.aop.IsLogin;
import com.qf.entity.Address;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * FileName: AddressController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/25 0:17
 */
@Controller
@RequestMapping("/address")
public class AddressController {

    @Reference
    private IAddressService addressService;

    @RequestMapping("/insertAddress")
    @IsLogin(mustLogin = true)
    public String insertAddress(Address address, User user){

        address.setUid(user.getId());
        addressService.insertAddress(address);
        return "redirect:/order/toOrderEdit";
    }

}
