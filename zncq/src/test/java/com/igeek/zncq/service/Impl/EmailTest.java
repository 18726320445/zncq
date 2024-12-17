package com.igeek.zncq.service.Impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/1 15:06
 * @email liuyia2022@163.com
 */
@SpringBootTest
public class EmailTest {
    @Autowired
    EmailService emailService;

    @Test
    public void emailSend(){
        emailService.sendMessage("2081088544@qq.com","Springboot邮件测试","你好呀王俊伟");
    }

}
