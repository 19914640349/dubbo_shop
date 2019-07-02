package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Role;
import com.qf.service.IRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * FileName: RoleController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/2 10:12
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Reference
    private IRoleService roleService;

    /**
     * 查询所有角色
     * @param model
     * @return
     */
    @RequestMapping("/roleList")
    public String roleList(Model model){
        List<Role> roles = roleService.queryAllRole();
        model.addAttribute("roles",roles);
        return "roleList";
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @RequestMapping("/addRole")
    public String addRole(Role role){
        roleService.addRole(role);
        return "redirect:/role/roleList";
    }

}
