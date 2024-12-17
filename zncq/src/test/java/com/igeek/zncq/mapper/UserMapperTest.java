package com.igeek.zncq.mapper;

import com.igeek.zncq.ZncqApplication;
import com.igeek.zncq.vo.UserVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ZncqApplication.class)
class UserMapperTest {
    @Autowired
    UserMapper userMapper;
    @Test
    void insert() {
//        User user = new User();
//        user.setLoginNumber("admin1");
//        user.setUserName("方昆");
//        String password = "123456";
//        String salt = "fk";
//        user.setSalt(salt);
//        password = PasswordUtils.getMd5Password(password,salt);
//        user.setPassword(password);
//        int res = userMapper.insert(user);
//        System.out.println(res);
//        System.out.println(userMapper.selectByUserNameUserAndPassword("admin").getUserName());
    }

    @Test
    void selectUserVo() {
        UserVo userVo = userMapper.selectUserVo(6);
        System.out.println(userVo);
    }
}