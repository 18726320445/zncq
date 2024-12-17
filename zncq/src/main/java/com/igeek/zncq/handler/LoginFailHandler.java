package com.igeek.zncq.handler;

import cn.hutool.json.JSONUtil;
import com.igeek.zncq.vo.ResultData;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/5 13:34
 * @email liuyia2022@163.com
 */
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ServletOutputStream outputStream = response.getOutputStream();
        ResultData<String> resultData = new ResultData<>(201, "登入失败");
        String str = JSONUtil.toJsonStr(resultData);
        outputStream.write(str.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
