package com.igeek.zncq.filter;

import cn.hutool.json.JSONUtil;
import com.igeek.zncq.vo.ResultData;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/8 20:41
 * @email liuyia2022@163.com
 */
@Component
public class MyLoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        OutputStream outputStream = response.getOutputStream();
        String res = JSONUtil.toJsonStr(new ResultData<>(405, "你还未登录，请先登入在访问"));
        response.sendRedirect("http://localhost:8080");
        outputStream.write(res.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();

    }
}
