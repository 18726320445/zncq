package com.igeek.zncq.redis;

import com.igeek.zncq.service.Impl.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/4 11:52
 * @email liuyia2022@163.com
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    JedisPool jedisPool;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;

    @Test
    public void test(){
        Jedis resource = jedisPool.getResource();
        String set = resource.set("name", "张三");
        String name = resource.get("name");
        System.out.println(set);
        System.out.println(name);
        resource.close();
    }

    @Test
    public void password(){
        String admin = passwordEncoder.encode("admin");
        System.out.println(admin);
    }
    @Test
    public void test1(){
        emailService.sendMessage("1445211359@qq.com","方昆","方昆大傻逼");
    }
}
