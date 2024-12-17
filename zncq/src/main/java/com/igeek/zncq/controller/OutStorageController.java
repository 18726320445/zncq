package com.igeek.zncq.controller;

import com.igeek.zncq.entity.OutStorage;
import com.igeek.zncq.service.IOutStorageService;
import com.igeek.zncq.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/28 09:18
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@RequestMapping("/outStorage")
public class OutStorageController {
    @Autowired
    IOutStorageService outStorageService;

    @GetMapping("/{pageNum}")
    public ResultData<PageVo<OutStorageVo>> findOutStorageAllByPage(@PathVariable("pageNum") Integer pageNum){
        ResultData<PageVo<OutStorageVo>> resultData = new ResultData<>();
        PageVo<OutStorageVo> pageVo = outStorageService.selectAllByPage(pageNum);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(pageVo);
        }else {
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @GetMapping("/good/{orderNo}")
    public ResultData<OrderVo> findAllGood(@PathVariable("orderNo") String orderNo){
        ResultData<OrderVo> resultData = new ResultData<>();
        OrderVo orderVo =  outStorageService.findOrderVoByOrderNo(orderNo);
        if (orderVo != null && orderVo.getGoods() != null){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(orderVo);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @GetMapping("/query")
    public ResultData<PageVo<OutStorageVo>> findOutStorageAllByQueryPage(OutStorageQueryVo queryVo){
        ResultData<PageVo<OutStorageVo>> resultData = new ResultData<>();
        PageVo<OutStorageVo> pageVo = outStorageService.selectAllByQueryPage(queryVo);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(pageVo);
        }else {
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @GetMapping("/finish/{pageNum}")
    public ResultData<PageVo<OutStorageVo>> findFinishOutStorage(@PathVariable("pageNum")Integer pageNum){
        ResultData<PageVo<OutStorageVo>> resultData = new ResultData<>();
        PageVo<OutStorageVo> pageVo = outStorageService.findFinishOutStoragePage(pageNum);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(pageVo);
        }else {
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }

    @PostMapping()
    public ResultData registerGood(@RequestBody OutStorageDto outStorageDto){
        ResultData<Object> resultData = new ResultData<>();
        outStorageService.outBound(outStorageDto);
        resultData.setCode(200);
        resultData.setMessage("出库成功");
        return resultData;
    }
    @GetMapping("/query2")
    public ResultData<PageVo<OutStorageVo>> findFinishOutStorageByQuery(OutStorageQueryVo queryVo){
        ResultData<PageVo<OutStorageVo>> resultData = new ResultData<>();
        PageVo<OutStorageVo> pageVo = outStorageService.findFinishOutStoragePageByQuery(queryVo);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(pageVo);
        }else {
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }

}
