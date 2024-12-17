package com.igeek.zncq.controller;

import com.igeek.zncq.service.ITransportService;
import com.igeek.zncq.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/1 09:12
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@RequestMapping("/transport")
@PreAuthorize("hasRole('ROLE_VEHICLE')")
public class TransportController {

    @Autowired
    ITransportService transportService;

    @GetMapping("/{pageNum}")
    public ResultData<PageVo<TransportVo>> findAllByPage(@PathVariable("pageNum") Integer pageNum){
        ResultData<PageVo<TransportVo>> resultData = new ResultData<>();
        PageVo<TransportVo> pageVo =  transportService.selectAllByPage(pageNum);
        if (pageVo.getTotal() != 0){
            resultData.setData(pageVo);
            resultData.setCode(200);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }

    @GetMapping
    public ResultData<PageVo<TransportVo>> findAllByQueryPage(TransportQueryVo queryVo){
        ResultData<PageVo<TransportVo>> resultData = new ResultData<>();
        PageVo<TransportVo> pageVo =  transportService.selectAllByQueryPage(queryVo);
        if (pageVo.getTotal() != 0){
            resultData.setData(pageVo);
            resultData.setCode(200);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @PostMapping("/receipt")
    public ResultData receipt(@RequestBody TransportDto transportDto){
        ResultData<Object> resultData = new ResultData<>();
        transportService.receipt(transportDto);
        resultData.setCode(200);
        resultData.setMessage("回单成功");
        return resultData;
    }
    @GetMapping("/order/{pageNum}")
    public ResultData<PageVo<OrderVo>> findTOAll(@PathVariable("pageNum") Integer pageNum){
        ResultData<PageVo<OrderVo>> resultData = new ResultData<>();
        PageVo<OrderVo> pageVo =  transportService.selectTOAll(pageNum);
        if (pageVo.getTotal() != 0){
            resultData.setData(pageVo);
            resultData.setCode(200);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @GetMapping("/order/query")
    public ResultData<PageVo<OrderVo>> findTOAllByQuery(TransportQueryVo queryVo){
        ResultData<PageVo<OrderVo>> resultData = new ResultData<>();
        PageVo<OrderVo> pageVo =  transportService.findTOAllByQuery(queryVo);
        if (pageVo.getTotal() != 0){
            resultData.setData(pageVo);
            resultData.setCode(200);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @GetMapping("/order/register/{orderNo}")
    public ResultData<List<GoodRawVo>> findOrderGoodRegister(@PathVariable("orderNo")String orderNo) {
        ResultData<List<GoodRawVo>> resultData = new ResultData<>();
        List<GoodRawVo> goodRawVos = transportService.findOrderGoodRegister(orderNo);
        if (goodRawVos != null && goodRawVos.size() != 0) {
            resultData.setData(goodRawVos);
            resultData.setCode(200);
            resultData.setMessage("查询成功");
        } else {
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @PutMapping("/address")
    public ResultData updateAddress(@RequestBody Map<String,String> map){
        String address = map.get("address");
        int id = Integer.parseInt(map.get("id"));
        transportService.updateAddress(address,id);
        return new ResultData<>(200,"更新成功");
    }

}
