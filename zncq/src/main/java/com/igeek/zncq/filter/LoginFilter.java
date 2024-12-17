package com.igeek.zncq.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/5 16:02
 * @email liuyia2022@163.com
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private boolean postOnly = true;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //  在这里取消原有的通过 request parameter 形式获取用户名密码的方法, 替换为 json 数据格式的获取
        ObjectMapper mapper = new ObjectMapper();
        UsernamePasswordAuthenticationToken authRequest = null;
        try (InputStream is = request.getInputStream()) {
            Map<String,String> authenticationBean = mapper.readValue(is, Map.class);
            authRequest = new UsernamePasswordAuthenticationToken(
                    authenticationBean.get("username"), authenticationBean.get("password"),null);
            SecurityContextHolder.getContext().setAuthentication(authRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 源码方法
        /*String username = obtainUsername(request);
        username = (username != null) ? username : "";
        username = username.trim();
        String password = obtainPassword(request);
        password = (password != null) ? password : "";
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);*/
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
