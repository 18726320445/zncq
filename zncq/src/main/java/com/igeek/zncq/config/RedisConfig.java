package com.igeek.zncq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/4 11:54
 * @email liuyia2022@163.com
 */
@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private  String host;

    @Value("${spring.redis.port}")
    private  int port;

    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        return new JedisPool(host,port);
    }

}
