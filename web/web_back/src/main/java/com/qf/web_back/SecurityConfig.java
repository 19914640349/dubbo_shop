package com.qf.web_back;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.service.IBackUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * FileName: SecurityConfig.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/4 17:11
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Reference
    private IBackUserService backUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                    .loginPage("/toLogin")
                    .loginProcessingUrl("/login")
                    .failureUrl("/toLogin?error=1")
                    .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .authorizeRequests()
                    .mvcMatchers("/resources/**").permitAll()
                    .mvcMatchers("/").authenticated()
                    .anyRequest().access("@perssionHandler.hasPerssion(request, authentication)")
                    //.anyRequest().authenticated()
                .and()
                .csrf().disable()
                .headers().frameOptions().sameOrigin()  //处理frame请求，让security放行
                .and()
                .exceptionHandling().accessDeniedPage("/noPerssion");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(backUserService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
