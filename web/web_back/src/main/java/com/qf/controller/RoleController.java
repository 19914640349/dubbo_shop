package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Role;
import com.qf.service.IRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping("/deleteRole/{id}")
    public String deleteRole(@PathVariable Integer id){
        roleService.deleteRole(id);
        return "redirect:/role/roleList";
    }

    /**
     * 根据用户的id查询所有角色并拥有哪些
     * @param uid   用户id
     * @return  所有的角色的ajax数据
     */
    @ResponseBody
    @RequestMapping("/ajaxRoleList")
    public List<Role> ajaxRoleList(Integer uid){
        return roleService.queryRolesByUid(uid);
    }

    /**
     * 修改角色的权限
     * @param rid
     * @param pids
     * @return
     */
    @RequestMapping("/updateRolePower")
    public String updateRolePower(Integer rid, Integer[] pids){
        roleService.updateRolePower(rid, pids);
        return "redirect:/role/roleList";
    }

}
