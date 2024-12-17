package com.igeek.zncq.mapper;

import com.igeek.zncq.entity.Role;
import com.igeek.zncq.entity.User;
import com.igeek.zncq.entity.UserExample;
import java.util.List;

import com.igeek.zncq.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

@Mapper
public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<Role> selectRoleByUid(@Param("id") Integer id);

    int insertUserAndRole(@Param("uid") Integer uid , @Param("rid") Integer rid);

    int deleteUserAndRoleByIds(@Param("ids") Integer[] ids);

    UserVo selectUserVo(@Param("id") Integer id);

}