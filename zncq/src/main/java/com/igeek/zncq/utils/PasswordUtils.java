package com.igeek.zncq.utils;

import org.springframework.util.DigestUtils;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/8 10:56
 * @email liuyia2022@163.com
 */
public class PasswordUtils {
        public static String getMd5Password(String password , String salt){
            for (int i = 0; i < 3; i++) {
                //md5加密
                password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes());
            }
            return password;
        }
}
