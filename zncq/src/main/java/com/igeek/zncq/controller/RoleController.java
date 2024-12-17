package com.igeek.zncq.controller;

import com.igeek.zncq.entity.Role;
import com.igeek.zncq.service.IRoleService;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/5 23:43
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@RequestMapping("/role")
@PreAuthorize("hasRole('ROLE_ROOT')")
public class RoleController {

    @Autowired
    IRoleService roleService;

    @GetMapping("/findAll")
    public ResultData<List<Role>> findAll(){
        ResultData<List<Role>> resultData = new ResultData<>();
        List<Role> roles = roleService.findAll();
        if (roles != null){
            resultData.setCode(200);
            resultData.setData(roles);
        }else {
            resultData.setCode(201);
            resultData.setMessage("没有角色信息");
        }
        return resultData;
    }
    @GetMapping("/{pageNum}")
    public ResultData<PageVo<Role>> findAllByPage(@PathVariable("pageNum")Integer pageNum){
        ResultData<PageVo<Role>> resultData = new ResultData<>();
        PageVo<Role> pageVo = roleService.findAllByPage(pageNum);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(pageVo);
        }else {
            resultData.setCode(201);
            resultData.setMessage("没有数据");
        }
        return resultData;
    }

    @PostMapping
    public ResultData addRole(@RequestBody Role role){
        ResultData<Object> resultData = new ResultData<>();
        roleService.insertRole(role);
        resultData.setCode(200);
        resultData.setMessage("创建成功");
        return resultData;
    }

    @DeleteMapping("/{id}")
    public ResultData deleteRole(@PathVariable("id") Integer id){
        roleService.deleteRole(id);
        return new ResultData(200,"删除成功");
    }
}
