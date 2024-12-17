package com.igeek.zncq.exception;

import com.igeek.zncq.vo.ResultData;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/14 18:53
 * @email liuyia2022@163.com
 */
@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(Throwable.class)
    public ResultData exceptionHandler(RuntimeException e){
        ResultData<Object> resultData = new ResultData<>();
        if (e instanceof AddException){
            resultData.setCode(402);
            resultData.setMessage(e.getMessage());
        }else if (e instanceof DeleteException){
            resultData.setCode(402);
            resultData.setMessage(e.getMessage());
        }else if (e instanceof UpdateException){
            resultData.setCode(402);
            resultData.setMessage(e.getMessage());
        }else if (e instanceof AccessDeniedException){
            resultData.setCode(403);
            resultData.setMessage("你没有权限操作");
        }else if (e instanceof AuthenticationException){
            resultData.setCode(404);
            resultData.setMessage("你还没有登入认证，请登入认证");
        }else {
            resultData.setCode(600);
            resultData.setMessage("发生未知异常");
            e.printStackTrace();
        }
        return resultData;
    }
}
