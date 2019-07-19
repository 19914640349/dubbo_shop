package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Email;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * FileName: SSOController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/18 23:56
 */
@Controller
@RequestMapping("/sso")
public class SsoController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Reference
    private IUserService userService;

    /**
     * 跳转到注册页面
     *
     * @return
     */
    @RequestMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 发送注册验证码
     *
     * @param email
     * @return
     */
    @ResponseBody
    @RequestMapping("/sendCode")
    public String sendCode(String email) {
        String topic = "淘宝网注册验证码";
        // 生成验证码
        int code = (int) (Math.random() * 9000) + 1000;
        String content = "注册的验证码为：" + code + "，如果不是本人操作，请忽略！";

        // 封装邮件实体类
        Email newEmail = new Email(email, topic, content);

        // 把验证码存到redis中
        redisTemplate.opsForValue().set(email + "_code", code);

        // 把邮件放到消息队列中
        rabbitTemplate.convertAndSend("email_exchange", "", newEmail);
        return "success";
    }

    /**
     * 用户注册
     *
     * @param user
     * @param code
     * @return
     */
    @RequestMapping("/register")
    public String register(User user, int code) {

        // 从redis中获取验证码
        Integer sendCode = (Integer) redisTemplate.opsForValue().get(user.getEmail() + "_code");

        // 判断验证码是否正确
        if (sendCode == null || sendCode != code) {
            return "redirect:/sso/toRegister?error=-3";
        }

        // 进行注册
        user.setCreateTime(new Date());
        int result = userService.register(user);
        if (result > 0) {
            // 注册成功，跳转到登录页面
            return "redirect:/sso/toLogin";
        }

        // 注册失败，回到注册页面并提示用户
        return "redirect:/sso/toRegister?error=" + result;
    }

}
