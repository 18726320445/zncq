package com.igeek.zncq.utils;




import com.igeek.zncq.entity.User;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/7 18:17
 * @email liuyia2022@163.com
 */

public class JwtUtils {
    //进行数字签名的私钥，一定要保管好
    private static final String secretKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9";


    /**
     * 用户登录成功后生成jwt
     * 使用Hs256算法
     * 三部分组成 头部+荷载+签证信息
     * @return
     */
    public static String acquireJWT(User user)  {
        //生成jwt令牌
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(user.getId().toString())
                //设置jwt
                .setSubject("token-check")
                //设置jwt主题
                .setIssuedAt(new Date())
                //设置jwt签发日期 时间一天 设置jwt的过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .claim("userId", user.getId())
                .claim("username", user.getUserName())
                .claim("password",user.getPassword())
                .signWith(SignatureAlgorithm.HS256, secretKey);
        return jwtBuilder.compact();
    }

    /**
     * 解析JWT字符串
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) {
        Claims body = null;
        //不管过不过期都返回
        try {
             body = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            body = e.getClaims();
        }
        return body;
    }
}

