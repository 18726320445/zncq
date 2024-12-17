package com.igeek.zncq.controller;

import com.igeek.zncq.entity.ProduceConsume;
import com.igeek.zncq.entity.Stock;
import com.igeek.zncq.service.IStockService;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.ResultData;
import com.igeek.zncq.vo.StockQueryVo;
import com.igeek.zncq.vo.StockVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/3/1 14:23
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@RequestMapping("/stock")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class StockController {
    @Autowired
    IStockService stockService;

    @GetMapping("/{pageNum}")
    public ResultData<PageVo<StockVo>> findALLByPage(@PathVariable("pageNum") Integer pageNum){
        ResultData<PageVo<StockVo>> resultData = new ResultData<>();
        PageVo<StockVo> pageVo = stockService.findAllByPage(pageNum);
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
    @GetMapping("/query")
    public ResultData<PageVo<StockVo>> findALLByQueryPage(StockQueryVo queryVo){
        ResultData<PageVo<StockVo>> resultData = new ResultData<>();
        PageVo<StockVo> pageVo = stockService.findAllByQueryPage(queryVo);
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
    @PostMapping("/pc")
    public ResultData Produce(@RequestBody ProduceConsume produceConsume){
        ResultData<Object> resultData = new ResultData<>();
        stockService.produce(produceConsume);
        resultData.setCode(200);
        resultData.setMessage("操作成功");
        return resultData;
    }
    @GetMapping("/warn")
    public ResultData<List<StockVo>> stockWarn(){
        ResultData<List<StockVo>> resultData = new ResultData<>();
        List<StockVo> stockVos =  stockService.warn();
        resultData.setCode(200);
        resultData.setData(stockVos);
        return resultData;
    }

}
