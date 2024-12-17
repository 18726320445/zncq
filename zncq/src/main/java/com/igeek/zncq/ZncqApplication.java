package com.igeek.zncq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.igeek.zncq.mapper"})
@SpringBootApplication
public class ZncqApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZncqApplication.class, args);
    }

}
