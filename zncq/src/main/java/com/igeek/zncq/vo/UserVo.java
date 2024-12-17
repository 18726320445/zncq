package com.igeek.zncq.vo;

import com.igeek.zncq.entity.Role;
import com.igeek.zncq.entity.User;
import lombok.Data;

import java.util.List;

/**
  * @version v1.0.0
  * @Description TODO
  * @createDate：2023/3/6 00:01
  * @author 刘燚
  *@email liuyia2022@163.com
*/
@Data
public class UserVo extends User {
   private List<Role> list;
}
