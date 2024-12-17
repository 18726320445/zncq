package com.igeek.zncq.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.Role;
import com.igeek.zncq.entity.RoleExample;
import com.igeek.zncq.exception.AddException;
import com.igeek.zncq.exception.DeleteException;
import com.igeek.zncq.mapper.RoleMapper;
import com.igeek.zncq.mapper.UserMapper;
import com.igeek.zncq.service.IRoleService;
import com.igeek.zncq.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/5 23:47
 * @email liuyia2022@163.com
 */
@Transactional(rollbackFor = {})
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<Role> findAll() {
        return roleMapper.selectByExample(new RoleExample());
    }

    @Override
    public PageVo<Role> findAllByPage(Integer pageNum) {
        PageVo<Role> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum,7);
        List<Role> roles = roleMapper.selectByExample(new RoleExample());
        PageInfo<Role> pageInfo = new PageInfo<>(roles, 5);
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setData(pageInfo.getList());
        return pageVo;
    }

    @Override
    public void insertRole(Role role) {
        int res = roleMapper.insertSelective(role);
        if (res != 1){
            throw new AddException("添加失败");
        }
    }

    @Override
    public void deleteRole(Integer id) {
        int count = roleMapper.countRoleisHavingUser(id);
        if (count != 0){
            throw new DeleteException("有用户正在使用该角色");
        }
        int res = roleMapper.deleteByPrimaryKey(id);
        if (res != 1){
            throw new DeleteException("删除失败,发生未知异常请重新操作");
        }
    }
}
