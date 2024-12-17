package com.igeek.zncq.service;

import com.igeek.zncq.entity.Role;
import com.igeek.zncq.vo.PageVo;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/5 23:47
 * @email liuyia2022@163.com
 */
public interface IRoleService {
    /**
     * 查询所有角色信息
     * @return
     */
    List<Role> findAll();

    /**
     * 根据页码查询角色信息
     * @param pageNum
     * @return
     */
    PageVo<Role> findAllByPage(Integer pageNum);

    /**
     * 添加角色
     * @param role
     */
    void insertRole(Role role);

    /**
     * 根据id删除角色
     * @param id
     */
    void deleteRole(Integer id);
}
