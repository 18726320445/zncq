package com.igeek.zncq.filter;

import cn.hutool.core.collection.CollectionUtil;
import com.igeek.zncq.entity.Role;
import com.igeek.zncq.service.IUserService;
import com.igeek.zncq.utils.JwtUtils;
import com.igeek.zncq.vo.UserVo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/7 18:51
 * @email liuyia2022@163.com
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    JedisPool jedisPool;
    @Autowired
    IUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        //若有无token则放行有后面过滤去处理
        if (token != null && !token.equals("") && !"null".equals(token)) {
            Claims claims = JwtUtils.parseJWT(token);
            String username = (String) claims.get("username");
            String password = (String) claims.get("password");
            Integer userId = (Integer) claims.get("userId");
            Jedis jedis = jedisPool.getResource();
            try {
                //判断token是否一致
                String userToken = jedis.get("token_" + username);
                if (userToken.equals(token)){
                    String key = username + "_" + password;
                    //在redis中取出角色信息,没有则回到数据库中寻找
                    Set<String> smembers = jedis.smembers(key);
                    ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    if (CollectionUtil.isNotEmpty(smembers)) {
                        smembers.stream().forEach(s -> authorities.add(new SimpleGrantedAuthority(s)));
                    } else {
                        UserVo userVo = userService.findUserVo(userId);
                        List<Role> roles = userVo.getList();
                        //存入Redis
                        roles.stream().forEach(role -> jedis.sadd(key,role.getRoleKey()));
                        //在加入到权限中
                        roles.stream().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleKey())));
                        //设置过期时间
                        jedis.expire(key,60*60);
                    }
                    //封装一个认证器
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                jedis.close();
            }
        }
        filterChain.doFilter(request, response);
    }
}
