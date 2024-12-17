package com.igeek.zncq.controller;

import com.igeek.zncq.service.IWarehouseTransferService;
import com.igeek.zncq.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/1 20:10
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@RequestMapping("/warehouseTransfer")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class WarehouseTransferController {
    @Autowired
    IWarehouseTransferService warehouseTransferService;

    @PostMapping
    public ResultData transfer(@RequestBody WarehouseTransferDto warehouseTransferDto){
        ResultData<Object> resultData = new ResultData<>();
        warehouseTransferService.transfer(warehouseTransferDto);
        resultData.setCode(200);
        resultData.setMessage("创建成功");
        return resultData;
    }

    @GetMapping("/{pageNum}")
    public ResultData<PageVo<WarehouseTransferVo>> findAll(@PathVariable("pageNum")Integer pageNum){
        ResultData<PageVo<WarehouseTransferVo>> resultData = new ResultData<>();
        PageVo<WarehouseTransferVo> pageVo = warehouseTransferService.findAll(pageNum);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(pageVo);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据记录");
        }
        return resultData;
    }

    @GetMapping("/query")
    public ResultData<PageVo<WarehouseTransferVo>> findAllByQuery(WarehouseTransferQueryVo queryVo){
        ResultData<PageVo<WarehouseTransferVo>> resultData = new ResultData<>();
        PageVo<WarehouseTransferVo> pageVo = warehouseTransferService.findAllByQuery(queryVo);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(pageVo);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据记录");
        }
        return resultData;
    }


}
