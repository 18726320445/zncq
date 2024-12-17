package com.igeek.zncq.vo;

import com.igeek.zncq.entity.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/6 00:02
 * @email liuyia2022@163.com
 */
@Data
public class UserDto {
    private Integer[] roleIds;
    private Integer id;
    private String userName;
    private String nickName;
    private Integer state;
    private String password;
    private String email;
    private String phone;
    private Date createTime;
}
