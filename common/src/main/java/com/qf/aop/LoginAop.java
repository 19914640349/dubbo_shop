package com.qf.aop;

import com.qf.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * FileName: LoginAop.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @date 2019/7/22 21:05
 */
@Component
@Aspect
public class LoginAop {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 对被IsLogin注解的方法进行进行环绕增强
     *
     * @param proceedingJoinPoint
     * @return
     */
    @Around("@annotation(IsLogin)")
    public Object isLogin(ProceedingJoinPoint proceedingJoinPoint) {

        // 1.获取cookie
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 拿到所有cookie
        Cookie[] cookies = request.getCookies();
        // 登录凭证
        String loginToken = null;

        if (cookies != null) {
            // 遍历
            for (Cookie cookie : cookies) {
                // 找到登录凭证的cookie
                if (cookie.getName().equals("loginToken")) {
                    loginToken = cookie.getValue();
                    break;
                }
            }
        }

        // 2.通过登录凭证从redis中获取用户信息
        User loginUser = null;
        if (loginToken != null) {
            loginUser = (User) redisTemplate.opsForValue().get(loginToken);
        }

        // 3.判断是否登录
        // 未登录的情况
        if (loginUser == null) {
            // 获取增强的目标签名
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            // 获取方法对象
            Method method = methodSignature.getMethod();
            // 获取方法上的注解
            IsLogin isLogin = method.getAnnotation(IsLogin.class);

            // 判断是否要求强制登录
            // 如果要强制登录
            if (isLogin.mustLogin()) {
                // 获取当前的请求地址
                String returnUrl = request.getRequestURL().toString();
                try {
                    // 对地址进行转码，以防乱码
                    returnUrl = URLEncoder.encode(returnUrl, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                // 条主电脑登录页面
                return "redirect:http://localhost:8084/sso/toLogin?returnUrl=" + returnUrl;
            }
        }

        // 4.已经登录，或者不用强制登录
        // 修改目标方法的实参列表，获得目标方法的实际参数列表
        Object[] args = proceedingJoinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null && args[i].getClass() == User.class) {
                // 覆盖原来的参数值
                args[i] = loginUser;
                break;
            }
        }

        // 5.用新的实参列表调用目标方法
        try {
            return proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

}
