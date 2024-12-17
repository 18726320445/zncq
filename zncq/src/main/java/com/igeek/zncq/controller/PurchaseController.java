package com.igeek.zncq.controller;

import com.igeek.zncq.entity.Purchase;
import com.igeek.zncq.service.IPurchaseService;
import com.igeek.zncq.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/20 16:32
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    IPurchaseService purchaseService;

    @GetMapping("/{pageNum}")
    public ResultData<PageVo<PurchaseVo>> findAll(@PathVariable("pageNum") Integer pageNum){
        ResultData<PageVo<PurchaseVo>> resultData = new ResultData<>();
        PageVo<PurchaseVo> pageVo = purchaseService.selectAll(pageNum);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @GetMapping("/state/query")
    public ResultData<PageVo<PurchaseVo>> findAllByQueryAndState(PurchaseDto purchaseDto){
        ResultData<PageVo<PurchaseVo>> resultData = new ResultData<>();
        PageVo<PurchaseVo> pageVo = purchaseService.selectAllByQueryAndState(purchaseDto);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @GetMapping("/state/{pageNum}/{state}")
    public ResultData<PageVo<PurchaseVo>> findAllByState(@PathVariable("pageNum") Integer pageNum,@PathVariable("state") Integer state){
        ResultData<PageVo<PurchaseVo>> resultData = new ResultData<>();
        PageVo<PurchaseVo> pageVo = purchaseService.findAllByState(pageNum, state);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @GetMapping("/query/{purchaseNo}")
    public ResultData<PurchaseVo> selectPurchaseVoByQuery(@PathVariable("purchaseNo") String purchaseNo){
        ResultData<PurchaseVo> resultData = new ResultData<>();
        PurchaseVo purchaseVo = purchaseService.selectPurchaseVoByNo(purchaseNo);
        if (purchaseVo != null){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(purchaseVo);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }

    @PostMapping
    public ResultData addPurchase(@RequestBody Purchase purchase){
        ResultData<Object> resultData = new ResultData<>();
        purchaseService.insertPurchase(purchase);
        resultData.setCode(200);
        resultData.setMessage("添加成功");
        return resultData;
    }
    @PostMapping("/good")
    public ResultData addGood(@RequestBody PurchaseDto purchaseDto){
        ResultData<Object> resultData = new ResultData<>();
        purchaseService.insertOrderGood(purchaseDto);
        resultData.setCode(200);
        resultData.setMessage("查询成功");
        return resultData;
    }

    @GetMapping()
    public ResultData<PurchaseGoodVo> findPurchaseGoodByQuery(PurchaseDto purchaseDto){
        ResultData<PurchaseGoodVo> resultData = new ResultData<>();
        PurchaseGoodVo res = purchaseService.selectPurchaseDtoByQuery(purchaseDto);
        resultData.setCode(200);
        resultData.setData(res);
        return resultData;
    }
    @PutMapping("/good")
    public ResultData updatePurchaseGoodByQuery(@RequestBody PurchaseDto purchaseDto){
        ResultData<Object> resultData = new ResultData<>();
        purchaseService.updatePurchaseGoodByQuery(purchaseDto);
        resultData.setCode(200);
        resultData.setMessage("修改成功");
        return  resultData;
    }

    @DeleteMapping("good")
    public ResultData deletePurchaseGoodByQuery(@RequestBody PurchaseDto purchaseDto){
        ResultData<Object> resultData = new ResultData<>();
        purchaseService.deletePurchaseGoodByQuery(purchaseDto);
        resultData.setCode(200);
        resultData.setMessage("修改成功");
        return  resultData;
    }

    @PutMapping("/{purchaseNo}")
    public ResultData updateState(@PathVariable("purchaseNo") String purchaseNo){
        ResultData<Object> resultData = new ResultData<>();
        purchaseService.updateState(purchaseNo);
        resultData.setCode(200);
        resultData.setMessage("提交成功,请等待审核通过");
        return resultData;
    }

    @PutMapping("/warehouse/yes/{purchaseNo}/{processName}/{warehouseId}")
    public ResultData agreePurchase(@PathVariable("purchaseNo") String purchaseNo,@PathVariable("processName")String processName,@PathVariable("warehouseId") Integer warehouseId){
        ResultData<Object> resultData = new ResultData<>();
        purchaseService.agreeOrder(purchaseNo,processName,warehouseId);
        resultData.setCode(200);
        resultData.setMessage("该采购订单通过,已发起入库通知等待入库");
        return resultData;
    }
    @DeleteMapping("/no/{purchaseNo}")
    public ResultData passPurchase(@PathVariable("purchaseNo") String purchaseNo){
        ResultData<Object> resultData = new ResultData<>();
        purchaseService.passOrderAndDelete(purchaseNo);
        resultData.setCode(200);
        resultData.setMessage("该采购订单已被取消");
        return resultData;
    }
    @GetMapping("query")
    public ResultData<PageVo<PurchaseVo>> findByQuery(PurchaseDto purchaseDto){
        ResultData<PageVo<PurchaseVo>> resultData = new ResultData<>();
        PageVo<PurchaseVo> pageVo = purchaseService.findAllByQuery(purchaseDto);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(pageVo);
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
}
