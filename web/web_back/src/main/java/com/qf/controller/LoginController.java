package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.service.IBackUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * FileName: LoginController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/3 16:09
 */
@Controller
@SessionAttributes("backUser")
public class LoginController {

    @Reference
    private IBackUserService backUserService;

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    /*@RequestMapping("/login")
    public String login(String username, String password, Model model){
        BackUser backUser = backUserService.login(username, password);
        if (backUser != null) {
            model.addAttribute("backUser", backUser);
            return "index";
        }
        model.addAttribute("login", "1");
        return "login";
    }*/
}
