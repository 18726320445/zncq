package com.igeek.zncq.controller;

import com.igeek.zncq.entity.Container;
import com.igeek.zncq.service.IContainerService;
import com.igeek.zncq.vo.ContainerQueryVo;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/15 18:48
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@RequestMapping("/container")
public class ContainerController {
    @Autowired
    IContainerService containerService;

    @GetMapping("/selectAll/{pageNum}")
    public ResultData<PageVo<Container>> findContainAll(@PathVariable("pageNum") Integer pageNum) {
        ResultData<PageVo<Container>> resultData = new ResultData<>();
        PageVo<Container> pageVo = containerService.selectContainAll(pageNum);
        if (pageVo.getTotal() != 0) {
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(pageVo);
        } else {
            resultData.setCode(201);
            resultData.setMessage("没有任何数据或出现未知异常");
        }
        return resultData;
    }

    @GetMapping("/query/{id}")
    public ResultData<Container> findContainerOne(@PathVariable("id") Integer id) {
        ResultData<Container> resultData = new ResultData<>();
        Container container = containerService.selectOne(id);
        if (container != null) {
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(container);
        } else {
            resultData.setCode(201);
            resultData.setMessage("不存在该容器信息!请刷新😊");
        }
        return resultData;
    }

    @GetMapping("/query")
    public ResultData<PageVo<Container>> findContainerByQuery(ContainerQueryVo containerQueryVo) {
        ResultData<PageVo<Container>> resultData = new ResultData<>();
        PageVo<Container> pageVo = containerService.selectByQuery(containerQueryVo);
        if (pageVo.getTotal() != 0) {
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        } else {
            resultData.setCode(201);
            resultData.setMessage("没有任何数据或发生未知异常");

        }
        return resultData;
    }

    @PostMapping()
    public ResultData addContainer(@RequestBody Container container) {
        ResultData<Object> resultData = new ResultData<>();
        containerService.insertContainer(container);
        resultData.setCode(200);
        resultData.setMessage("添加成功");
        return resultData;
    }

    @PutMapping()
    public ResultData updateContainerById(@RequestBody Container container) {
        ResultData<Object> resultData = new ResultData<>();
        containerService.updateById(container);
        resultData.setCode(200);
        resultData.setMessage("修改成功");
        return resultData;
    }

    @DeleteMapping()
    public ResultData deleteContainer(Integer[] ids) {
        ResultData<Object> resultData = new ResultData<>();
        containerService.deleteContainerById(ids);
        resultData.setCode(200);
        resultData.setMessage("删除成功");
        return resultData;
    }

    @GetMapping("/findAll")
    public ResultData<List<Container>> findAllContainer(@RequestParam(value = "ids",required = false) Integer[] ids){
        ResultData<List<Container>> resultData = new ResultData<>();
        List<Container> containers = containerService.selectAll(ids);
        if (containers.size() != 0){
            resultData.setCode(200);
            resultData.setData(containers);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何容器信息");
        }
        return resultData;
    }

}
