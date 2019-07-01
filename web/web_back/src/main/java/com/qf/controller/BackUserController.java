package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.BackUser;
import com.qf.service.IBackUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * FileName: BackUserController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/1 20:10
 */
@Controller
@RequestMapping("/backUser")
public class BackUserController {

    @Reference
    private IBackUserService backUserService;

    /**
     * 查询所有后台用户的信息
     * @param model
     * @return
     */
    @RequestMapping("/userList")
    public String userList(Model model){
        List<BackUser> users = backUserService.queryAll();
        model.addAttribute("users",users);
        return "backUserList";
    }

    /**
     * 添加职工
     * @param backUser
     * @return
     */
    @RequestMapping("/addBackUser")
    public String addBackUser(BackUser backUser){
        backUserService.addBackUser(backUser);
        return "redirect:/backUser/userList";
    }

    @RequestMapping("/deleteBackUser/{id}")
    public String deleteBackUser(@PathVariable Integer id){
        backUserService.deleteBackUser(id);
        return "redirect:/backUser/userList";
    }

}
