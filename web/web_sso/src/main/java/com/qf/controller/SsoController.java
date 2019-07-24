package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qf.entity.Email;
import com.qf.entity.User;
import com.qf.service.ICartService;
import com.qf.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * FileName: SSOController.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/18 23:56
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

    @Reference
    private ICartService cartService;

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
     * @return 页面地址
     */
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 发送注册验证码
     *
     * @param email 邮箱地址
     * @return 状态
     */
    @ResponseBody
    @RequestMapping("/sendCode")
    public String sendCode(String email) {
        String topic = "淘宝网注册验证码";
        // 生成验证码
        int code = (int) (Math.random() * 9000) + 1000;
        String content = "注册的验证码为：" + code + "，有效期15分钟，如果不是本人操作，请忽略！";

        // 封装邮件实体类
        Email newEmail = new Email(email, topic, content);

        // 把验证码存到redis中
        redisTemplate.opsForValue().set(email + "_code", code);
        redisTemplate.expire(email + "_code", 15, TimeUnit.MINUTES);

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

    /**
     * 跳转到忘记密码页面
     *
     * @return
     */
    @RequestMapping("/toForgetPassword")
    public String toForgetPassword() {
        return "forgetPassword";
    }

    /**
     * 给用户邮箱发送忘记密码邮件
     *
     * @param username
     * @return
     */
    @RequestMapping("/forgetPassword")
    @ResponseBody
    public Object forgetPassword(String username) {
        // 根据用户名查询用户信息
        User user = userService.queryByUsername(username);

        Map<String, Object> map = new HashMap<>();
        // 用户不存在
        if (user == null) {
            map.put("code", "-1");
            return map;
        }

        // 生成一个凭证
        String token = UUID.randomUUID().toString();
        // 把凭证放入redis中
        redisTemplate.opsForValue().set(username + "_token", token);
        redisTemplate.expire(username + "_token", 15, TimeUnit.MINUTES);

        // 制作邮件
        String topic = "淘宝网找回密码";
        String url = "http://localhost:8084/sso/toResetPassword?username=" + username + "&token=" + token;
        String content = "找回密码请<a href='" + url + "'>点击这里</a>，有效期15分钟，如果不是本人操作，请忽略！";
        Email email = new Email(user.getEmail(), topic, content);

        // 发送邮件
        rabbitTemplate.convertAndSend("email_exchange", "", email);
        map.put("code", "1");

        // 提示用户信息
        String emailStr = user.getEmail();
        int index = emailStr.indexOf("@");

        // 设置邮箱信息，比如1454******@qq.com
        String emailInfo = emailStr.replace(emailStr.substring(4, index), "******");
        map.put("emailInfo", emailInfo);

        // 设置跳转邮箱登录地址
        String emailLogin = "http://mail." + emailStr.substring(index + 1);
        map.put("emailLogin", emailLogin);
        return map;
    }

    /**
     * 跳转到重置密码页面
     *
     * @return
     */
    @RequestMapping("/toResetPassword")
    public String toResetPassword() {
        return "resetPassword";
    }

    /**
     * 重置密码
     *
     * @param username
     * @param password
     * @param token
     * @return
     */
    @RequestMapping("/resetPassword")
    public String resetPassword(String username, String password, String token) {
        // 从redis中获取凭证
        String userToken = (String) redisTemplate.opsForValue().get(username + "_token");
        if (token.equals(userToken)) {
            // 通过凭证验证
            userService.resetPassword(username, password);
            // 删除redis中的凭证
            redisTemplate.delete(username + "_token");
            return "redirect:/sso/toLogin";
        }
        return "redirect:/sso/toForgetPassword?error=-1";
    }

    /**
     * 登录并返回登陆前的页面
     *
     * @param username
     * @param password
     * @param returnUrl
     * @param response
     * @return
     */
    @RequestMapping("/login")
    public String login(@CookieValue(name = "cartToken", required = false) String cartToken,
            String username, String password, String returnUrl, HttpServletResponse response) {

        User user = userService.login(username, password);
        // 登录失败，重定向到登录页面
        if (user == null) {
            return "redirect:/sso/toLogin?error=-1";
        }

        String token = UUID.randomUUID().toString();
        // 把用户存到cookie中，保存7天
        Cookie cookie = new Cookie("loginToken", token);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/");
        response.addCookie(cookie);

        // 登录成功，把用户存到redis中，保存7天
        redisTemplate.opsForValue().set(token, user);
        redisTemplate.expire(token, 7, TimeUnit.DAYS);

        // 合并未登录时的购物车
        int result = cartService.mergeCart(cartToken, user);
        if (result == 1) {
            // 清除临时购物车的cookie
            Cookie cartCookie = new Cookie("cartToken", "");
            cartCookie.setMaxAge(0);
            cartCookie.setPath("/");
            response.addCookie(cartCookie);
        }

        // 返回到登录前的页面
        return "redirect:" + returnUrl;
    }

    /**
     * 验证用户是否登录
     *
     * @param loginToken
     * @param callback
     * @return
     */
    @RequestMapping("/checkLogin")
    @ResponseBody
    public String checkLogin(@CookieValue(name = "loginToken", required = false) String loginToken, String callback) {
        User user = null;
        if (loginToken != null) {
            // 验证能否从redis中拿到拿到这个cookie的用户信息
            user = (User) redisTemplate.opsForValue().get(loginToken);
        }

        // 获取返回的json
        String userJson = user != null ? JSON.toJSONString(user) : null;

        // 返回的json类型
        return callback != null ? (callback + "(" + userJson + ")") : userJson;
    }

    /**
     * 注销登录
     *
     * @param loginToken
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    public String logout(@CookieValue(name = "loginToken", required = false) String loginToken, HttpServletResponse response){

        // 清除redis中的缓存
        if (loginToken != null) {
            redisTemplate.delete(loginToken);
        }

        // 清除cookie
        Cookie cookie = new Cookie("loginToken", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/sso/toLogin";
    }

}
