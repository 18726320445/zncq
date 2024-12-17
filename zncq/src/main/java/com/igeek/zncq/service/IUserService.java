package com.igeek.zncq.service;

import com.igeek.zncq.entity.Log;
import com.igeek.zncq.entity.User;
import com.igeek.zncq.vo.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

public interface IUserService extends UserDetailsService {

    /**
     * 登入
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);

    /**
     * 创建新用户
     * @param userDto
     */
    void add(UserDto userDto);

    /**
     * 修改用户信息
     * @param userDto
     */
    void update2(UserDto userDto);

    /**
     * 根据id查找用户信息
     * @param id
     * @return
     */
    User findOne(Integer id);

    User findOneByUsernameByPassword(String username);

    /**
     * 查询所有
     * @param pageNum
     * @return
     */
    PageVo<User> findAll(Integer pageNum);

    /**
     * 根据id 批量删除
     * @param ids
     */
    void deleteByIds(Integer[] ids);

    /**
     * 查找对应的用户信息以及拥有的角色信息
     * @param id
     * @return
     */
    UserVo findUserVo(Integer id);

    /**
     * 修改用户信息
     * @param user
     */
    void update1(User user);

    /**
     * 根据条件查询
     * @param queryVo
     * @return
     */
    PageVo<User> findAllByQuery(UserQueryVo queryVo);

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     */
    void updatePwd(String oldPassword, String newPassword,PasswordEncoder passwordEncoder);

    /**
     * 通过uid 修改密码
     * @param id
     * @param password
     * @param passwordEncoder
     */
    void updatePwdById(int id, String password,String rootPassword ,PasswordEncoder passwordEncoder);

    /**
     * 查询所有日志
     * @param pageNum
     * @return
     */
    PageVo<Log> findAllLog(Integer pageNum);

    /**
     * 根据条件查询日志
     * @param queryVo
     * @return
     */
    PageVo<Log> findAllLogByQuery(LogQueryVo queryVo);
}
