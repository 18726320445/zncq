package com.igeek.zncq.controller;

import com.igeek.zncq.entity.Log;
import com.igeek.zncq.entity.User;
import com.igeek.zncq.service.IUserService;
import com.igeek.zncq.utils.PasswordUtils;
import com.igeek.zncq.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/7 20:41
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@PreAuthorize("hasRole('ROLE_ROOT')")
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping("/login")
    public ResultData login(@RequestBody Map<String,String> map){
        String username = map.get("username");
        String password = map.get("password");
        log.info(username + "===" +password);
        ResultData<User> resultData = new ResultData<>();
        //校验数据是否正常
        if (StringUtils.hasLength(username) || StringUtils.hasLength(password)){
            //根据账号查找对应的用户
            User user = userService.login(username,password);
            if (user != null){
                resultData.setCode(200);
                resultData.setMessage("登入成功");
                resultData.setData(user);
            }else {
                resultData.setCode(401);
                resultData.setMessage("该用户不存在");
            }
        }else{
            resultData.setCode(400);
            resultData.setMessage("输入不能为空");
        }
        return resultData;
    }

    @PostMapping("/user")
    public ResultData add(@RequestBody UserDto userDto){
        ResultData<Object> resultData = new ResultData<>();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.add(userDto);
        resultData.setCode(200);
        resultData.setMessage("添加成功");
        return resultData;
    }
    @PutMapping("/user")
    public ResultData update(@RequestBody User user){
        ResultData<Object> resultData = new ResultData<>();
        userService.update1(user);
        resultData.setCode(200);
        resultData.setMessage("修改成功成功");
        return resultData;
    }
    @PutMapping("/userRole")
    public ResultData update(@RequestBody UserDto userDto){
        ResultData<Object> resultData = new ResultData<>();
        userService.update2(userDto);
        resultData.setCode(200);
        resultData.setMessage("修改成功成功");
        return resultData;
    }
    @GetMapping("/user/me")
    public ResultData<User> findMe(){
        ResultData<User> resultData = new ResultData<>();
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findOneByUsernameByPassword(principal);
        if (user != null){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(user);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有该用户");
        }
        return resultData;
    }
    @GetMapping("/user/findOne/{id}")
    public ResultData<User> findOne(@PathVariable("id") Integer id){
        ResultData<User> resultData = new ResultData<>();
        User user = userService.findOne(id);
        if (user != null){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(user);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有该用户");
        }
        return resultData;
    }
    @GetMapping("/user/findAll/{pageNum}")
    public ResultData<PageVo<User>> findAll(@PathVariable("pageNum")Integer pageNum){
        ResultData<PageVo<User>> resultData = new ResultData<>();
        PageVo<User> pageVo = userService.findAll(pageNum);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(pageVo);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有数据");
        }
        return resultData;
    }
    @GetMapping("/user/findAll")
    public ResultData<PageVo<User>> findAllByQuery(UserQueryVo queryVo){
        ResultData<PageVo<User>> resultData = new ResultData<>();
        PageVo<User> pageVo = userService.findAllByQuery(queryVo);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(pageVo);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有数据");
        }
        return resultData;
    }
    @DeleteMapping("/user")
    public ResultData deleteUsers(@RequestParam("ids") Integer[] ids){
        ResultData resultData = new ResultData();
        userService.deleteByIds(ids);
        resultData.setCode(200);
        resultData.setMessage("删除成功");
        return resultData;
    }

    @GetMapping("/user/findVo/{id}")
    public ResultData<UserVo> findUserVo (@PathVariable("id") Integer id){
        ResultData<UserVo> resultData = new ResultData<>();
        UserVo userVo = userService.findUserVo(id);
        if (userVo != null){
            resultData.setCode(200);
            resultData.setData(userVo);
        }else{
            resultData.setCode(201);
            resultData.setMessage("该用户不存在");
        }
        return resultData;
    }
    @GetMapping("/username")
    public ResultData<String> getUserName(){
        return new ResultData<>(200,"登入成功", (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @PutMapping("/user/pwd")
    public ResultData updatePwd(@RequestBody Map<String,String> map){
        ResultData<Object> resultData = new ResultData<>();
        userService.updatePwd(map.get("oldPassword"),map.get("newPassword"),passwordEncoder);
        resultData.setCode(200);
        resultData.setMessage("修改成功,请重新登入");
        return resultData;
    }
    @PutMapping("/user/rootPwd")
    public ResultData updatePwdById(@RequestBody Map<String,Object> map){
        ResultData<Object> resultData = new ResultData<>();
        userService.updatePwdById((int)map.get("id"),(String)map.get("password"),(String) map.get("rootPassword"),passwordEncoder);
        resultData.setCode(200);
        resultData.setMessage("修改成功");
        return resultData;
    }
    @GetMapping("/user/log/{pageNum}")
    public ResultData<PageVo<Log>> findAllLog(@PathVariable("pageNum") Integer pageNum){
        ResultData<PageVo<Log>> resultData = new ResultData<>();
        PageVo<Log> pageVo = userService.findAllLog(pageNum);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setData(pageVo);
        }else{
            resultData.setCode(201);
        }
        return resultData;
    }

    @GetMapping("user/log")
    public ResultData<PageVo<Log>> findAllLogByQuery(LogQueryVo queryVo){
        ResultData<PageVo<Log>> resultData = new ResultData<>();
        PageVo<Log> pageVo = userService.findAllLogByQuery(queryVo);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setData(pageVo);
        }else{
            resultData.setCode(201);
        }
        return resultData;
    }
}
