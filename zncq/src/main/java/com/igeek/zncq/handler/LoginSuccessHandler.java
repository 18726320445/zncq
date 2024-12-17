package com.igeek.zncq.handler;

import cn.hutool.json.JSONUtil;
import com.igeek.zncq.log.LogSys;
import com.igeek.zncq.service.IUserService;
import com.igeek.zncq.utils.JwtUtils;
import com.igeek.zncq.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/5 13:30
 * @email liuyia2022@163.com
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    IUserService userService;
    @Autowired
    JedisPool jedisPool;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        ServletOutputStream outputStream = response.getOutputStream();
        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();
        com.igeek.zncq.entity.User user = userService.findOneByUsernameByPassword(username);
        String token = JwtUtils.acquireJWT(user);
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.set("token_"+username,token);
            ResultData<String> resultData = new ResultData<>(200, "登入成功", token);
            String str = JSONUtil.toJsonStr(resultData);
            outputStream.write(str.getBytes());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
            outputStream.close();
        }
    }

}
