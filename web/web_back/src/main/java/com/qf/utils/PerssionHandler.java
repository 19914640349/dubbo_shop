package com.qf.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * FileName: PerssionHandler.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/4 17:31
 */
@Component
public class PerssionHandler {

    public boolean hasPerssion(HttpServletRequest request, Authentication authentication){

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        Object principal = authentication.getPrincipal();

        // 判断是否通过认证
        if(principal instanceof UserDetails) {
            String requestURI = request.getRequestURI();

            UserDetails userDetails = (UserDetails) principal;
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (antPathMatcher.match(requestURI, authority.getAuthority())) {
                    return true;
                }
            }
        }
        return false;
    }
}
