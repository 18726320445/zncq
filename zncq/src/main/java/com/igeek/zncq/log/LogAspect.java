package com.igeek.zncq.log;

import cn.hutool.json.JSONUtil;
import com.igeek.zncq.entity.Log;
import com.igeek.zncq.entity.User;
import com.igeek.zncq.entity.UserExample;
import com.igeek.zncq.mapper.UserMapper;
import com.igeek.zncq.service.ILogService;
import com.igeek.zncq.vo.ResultData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.stream.Stream;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/3 20:38
 * @email liuyia2022@163.com
 */
@Aspect
@Component
public class LogAspect {
    @Autowired
    ILogService logService;
    @Autowired
    UserMapper userMapper;

    private final Log log = new Log();

    @Pointcut("@annotation(com.igeek.zncq.log.LogSys)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long l = System.currentTimeMillis();
        log.setDoTime(new Date());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = userMapper.selectByExample(userExample).get(0);
        log.setUserId(user.getId());
        ResultData proceed = (ResultData) joinPoint.proceed();
        long l1 = System.currentTimeMillis();
        long time = l1 - l;
        String message = proceed.getMessage();
        saveLog(joinPoint, time, message);
        return proceed;
    }

    public void saveLog(ProceedingJoinPoint joinPoint, long time, String message) {
        log.setProcessTime((int) time);
        log.setResultMessage(message);
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String annoMessage = "";
        LogSys annotation = method.getAnnotation(LogSys.class);
        if (annoMessage != null) {
            annoMessage = annotation.value();
        }
        log.setDetail(annoMessage);
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                continue;
            }
            arguments[i] = args[i];
        }
        String arg = JSONUtil.toJsonStr(arguments);
        log.setArgs(arg);
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        String ipAddr = getIpAddr(request);
        log.setIp(ipAddr);
        log.setType(signature.getName());
        logService.save(log);
    }

    // 获取真实ip地址
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("127.0.0.1".equals(ip) || ip == "127.0.0.1" || "0:0:0:0:0:0:0:1".equals(ip) || ip == "0:0:0:0:0:0:0:1") {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }
}
