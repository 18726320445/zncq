package com.igeek.zncq.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.*;
import com.igeek.zncq.exception.AddException;
import com.igeek.zncq.exception.DeleteException;
import com.igeek.zncq.exception.UpdateException;
import com.igeek.zncq.log.LogSys;
import com.igeek.zncq.mapper.LogMapper;
import com.igeek.zncq.mapper.UserMapper;
import com.igeek.zncq.service.IUserService;
import com.igeek.zncq.utils.JwtUtils;
import com.igeek.zncq.vo.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/7 20:40
 * @email liuyia2022@163.com
 */
@Transactional(rollbackFor = {})
@Service
public class UserServiceImp implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    JedisPool jedisPool;
    @Autowired
    LogMapper logMapper;

    @Override
    public User login(String username, String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPasswordEqualTo(password).andUserNameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (users == null || users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public void add(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setCreateTime(new Date());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(user.getUserName());
        List<User> users = userMapper.selectByExample(userExample);
        if (!CollectionUtil.isEmpty(users)){
            throw new AddException("该用户以存在");
        }
        int i = userMapper.insertSelective(user);
        if (i != 1) {
            throw new AddException("用户添加失败");
        }
        Stream.of(userDto.getRoleIds()).forEach(rid -> {
            int i1 = userMapper.insertUserAndRole(user.getId(), rid);
            if (i1 != 1) {
                throw new AddException("角色添加失败");
            }
        });
    }

    @Override
    public void update2(UserDto userDto) {
        //删除关系表
        userMapper.deleteUserAndRoleByIds(new Integer[]{userDto.getId()});
        //增加
        Stream.of(userDto.getRoleIds()).forEach(rid -> {
            int i = userMapper.insertUserAndRole(userDto.getId(), rid);
            if (i != 1) {
                throw new AddException("角色信息更新失败");
            }
        });
        //更新用户信息
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        int res = userMapper.updateByPrimaryKeySelective(user);
        if (res != 1) {
            throw new UpdateException("用户信息修改失败,请重新操作");
        }
        //更新后，同时更新redis 中用户角色信息
        UserVo userVo = userMapper.selectUserVo(user.getId());
        String key = userVo.getUserName() + "_" + userVo.getPassword();
        List<Role> roles = userVo.getList();
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.del(key);
            roles.stream().forEach(role -> jedis.sadd(key, role.getRoleKey()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    @Override
    public User findOne(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User findOneByUsernameByPassword(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(username);
        return userMapper.selectByExample(userExample).get(0);
    }

    @Override
    public PageVo<User> findAll(Integer pageNum) {
        PageVo<User> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum, 7);
        List<User> users = userMapper.selectByExample(new UserExample());
        PageInfo<User> pageInfo = new PageInfo<>(users, 5);
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setData(pageInfo.getList());
        return pageVo;
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        //删除关系表
        userMapper.deleteUserAndRoleByIds(ids);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(Arrays.asList(ids));
        int i = userMapper.deleteByExample(userExample);
        if (i != ids.length) {
            throw new DeleteException("删除失败,发生未知异常请重新操作");
        }
        //删除后，同时删除redis中用户角色信息
        Jedis jedis = jedisPool.getResource();
        try {
            Stream.of(ids).forEach(id -> {
                User user = userMapper.selectByPrimaryKey(id);
                String key = user.getUserName() + "_" + user.getPassword();
                jedis.del(key);
                jedis.del("token_" + user.getUserName());
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

    }

    @Override
    public UserVo findUserVo(Integer id) {
        return userMapper.selectUserVo(id);
    }

    @Override
    public void update1(User user) {
        int res = userMapper.updateByPrimaryKeySelective(user);
        if (res != 1) {
            throw new UpdateException("更新失败");
        }
        if (user.getState() == 0) {
            //更新状态后，同时删除redis 中用户角色信息
            User user1 = userMapper.selectByPrimaryKey(user.getId());
            String key = user1.getUserName() + "_" + user1.getPassword();
            Jedis jedis = jedisPool.getResource();
            try {
                jedis.del(key);
                jedis.del("token_" + user1.getUserName());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                jedis.close();
            }
        }
    }

    @Override
    public PageVo<User> findAllByQuery(UserQueryVo queryVo) {
        PageVo<User> pageVo = new PageVo<>();
        PageHelper.startPage(queryVo.getPageNum(), 7);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (queryVo.getState() != null) {
            criteria.andStateEqualTo(queryVo.getState());
        }
        if (StringUtils.hasLength(queryVo.getUsername())) {
            criteria.andNickNameLike("%" + queryVo.getUsername() + "%");
        }
        List<User> users = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(users, 5);
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setData(pageInfo.getList());
        return pageVo;
    }

    @Override
    public void updatePwd(String oldPassword, String newPassword, PasswordEncoder passwordEncoder) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(principal);
        List<User> users = userMapper.selectByExample(userExample);
        User user = users.get(0);
        //对比密码
        boolean matches = passwordEncoder.matches(oldPassword, user.getPassword());
        if (!matches) {
            throw new UpdateException("旧密码对比不一致，拒绝更改");
        }
        //修改密码
        user.setPassword(passwordEncoder.encode(newPassword));
        int res = userMapper.updateByPrimaryKeySelective(user);
        if (res != 1) {
            throw new UpdateException("修改异常，请重新操作");
        }
        //redis更新token
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.del("token_"+user.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    @Override
    public void updatePwdById(int id, String password, String rootPassword,PasswordEncoder passwordEncoder) {
        User user = userMapper.selectByPrimaryKey(id);
        String oldPwd = passwordEncoder.encode(user.getPassword());
        String newPwd = passwordEncoder.encode(password);
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(principal);
        User user1 = userMapper.selectByExample(userExample).get(0);
        if (!passwordEncoder.matches(rootPassword,user1.getPassword())){
            throw new UpdateException("密码错误");
        }
        user.setPassword(newPwd);
        int res = userMapper.updateByPrimaryKeySelective(user);
        if (res != 1) {
            throw new UpdateException("修改异常，请重新操作");
        }
        //更新redis
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.del("token_"+user.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    @Override
    public PageVo<Log> findAllLog(Integer pageNum) {
        PageVo<Log> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum,7);
        List<Log> logs = logMapper.selectByExample(new LogExample());
        PageInfo<Log> pageInfo = new PageInfo<>(logs, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setTotal((int) pageInfo.getTotal());
        return pageVo;
    }

    @Override
    public PageVo<Log> findAllLogByQuery(LogQueryVo queryVo) {
        PageVo<Log> pageVo = new PageVo<>();
        PageHelper.startPage(queryVo.getPageNum(),7);
        LogExample logExample = new LogExample();
        LogExample.Criteria criteria = logExample.createCriteria();
        if (queryVo.getUserId() != null){
            criteria.andUserIdEqualTo(queryVo.getUserId());
        }
        if (StringUtils.hasLength(queryVo.getIp())){
            criteria.andIpEqualTo(queryVo.getIp());
        }
        List<Log> logs = logMapper.selectByExample(logExample);
        PageInfo<Log> pageInfo = new PageInfo<>(logs, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setTotal((int) pageInfo.getTotal());
        return pageVo;
    }

//    @LogSys("登入操作")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (CollectionUtil.isNotEmpty(users)) {
            User user = users.get(0);
            if (user != null) {
                List<Role> roles = userMapper.selectUserVo(user.getId()).getList();
                List<SimpleGrantedAuthority> list = new ArrayList<>();
                roles.stream().forEach(role -> list.add(new SimpleGrantedAuthority(role.getRoleKey())));
                return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), user.getState() == 1, true, true, true, list);
            }
        }
        return null;
    }


}
