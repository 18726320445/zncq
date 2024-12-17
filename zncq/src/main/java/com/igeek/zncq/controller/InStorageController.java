package com.igeek.zncq.controller;


import com.igeek.zncq.entity.InStorage;
import com.igeek.zncq.service.IInStorageService;
import com.igeek.zncq.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/23 10:56
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@RequestMapping("inStorage")
public class InStorageController {

    @Autowired
    IInStorageService inStorageService;
    @GetMapping("/query2")
    public ResultData<PageVo<InStorageVo>> findAllByQuery(InStorageQueryVo queryVo){
        ResultData<PageVo<InStorageVo>> resultData = new ResultData<>();
        PageVo<InStorageVo> pageVo = inStorageService.selectInStorageVoByQuery2(queryVo);
        if(pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询采购");
            resultData.setData(pageVo);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @GetMapping
    public ResultData<InStorage> findOne(@RequestParam("orderNo") String orderNo,@RequestParam("goodId")Integer goodId){
        ResultData<InStorage> resultData = new ResultData<>();
        InStorage inStorage = inStorageService.selectOneByOrderNoAndGoodId(orderNo,goodId);
        if (inStorage != null){
            resultData.setData(inStorage);
            resultData.setCode(200);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有该入库记录");
        }
        return resultData;
    }
    @GetMapping("/{pageNum}")
    public ResultData<PageVo<InStorageVo>> findAllForPage(@PathVariable("pageNum") Integer pageNum){
        ResultData<PageVo<InStorageVo>> resultData = new ResultData<>();
        PageVo<InStorageVo> pageVo = inStorageService.selectInStorageVoByPage(pageNum);
        if(pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询采购");
            resultData.setData(pageVo);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @GetMapping("/detail/{orderNo}")
    public ResultData<OrderVo> findAllGoodByOrderNo(@PathVariable("orderNo")String orderNo){
        ResultData<OrderVo> resultData = new ResultData<>();
        OrderVo orderVo = inStorageService.selectAllGoodByOrderNo(orderNo);
        if (orderVo != null && orderVo.getGoods().size() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(orderVo);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有数据记录");
        }
        return resultData;
    }
    @GetMapping("/query")
    public ResultData<PageVo<InStorageVo>> findAllForPageByQuery(InStorageQueryVo queryVo){
        ResultData<PageVo<InStorageVo>> resultData = new ResultData<>();
        PageVo<InStorageVo> pageVo = inStorageService.selectInStorageVoByQuery(queryVo);
        if(pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(pageVo);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @GetMapping("/finish/{pageNum}")
    public ResultData<PageVo<InStorageVo>> findAll(@PathVariable("pageNum")Integer pageNum){
        ResultData<PageVo<InStorageVo>> resultData = new ResultData<>();
        PageVo<InStorageVo> pageVo = inStorageService.selectInStorageVoByInDate(pageNum);
        if(pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询采购");
            resultData.setData(pageVo);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }

    @PostMapping()
    public ResultData register(@RequestBody InStorage inStorage){
        ResultData<Object> resultData = new ResultData<>();
        inStorageService.register(inStorage);
        resultData.setCode(200);
        resultData.setMessage("登记成功");
        return  resultData;
    }
    @PutMapping
    public ResultData update(@RequestBody InStorage inStorage){
        ResultData<Object> resultData = new ResultData<>();
        inStorageService.updateRegist(inStorage);
        resultData.setCode(200);
        resultData.setMessage("修改成功");
        return  resultData;
    }
}
