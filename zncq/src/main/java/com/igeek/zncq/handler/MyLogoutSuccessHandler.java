package com.igeek.zncq.handler;

import cn.hutool.json.JSONUtil;
import com.igeek.zncq.log.LogSys;
import com.igeek.zncq.vo.ResultData;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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
 * @createDate：2023/3/9 10:53
 * @email liuyia2022@163.com
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ServletOutputStream outputStream = response.getOutputStream();
        String res = JSONUtil.toJsonStr(new ResultData<>(200, "登出成功"));
        outputStream.write(res.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }
}
