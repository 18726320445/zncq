package com.igeek.zncq.controller;

import com.igeek.zncq.entity.Good;
import com.igeek.zncq.exception.FileUpLoadException;
import com.igeek.zncq.mapper.GoodMapper;
import com.igeek.zncq.service.IGoodService;
import com.igeek.zncq.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.UUID;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/24 20:45
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@RequestMapping("/good")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class EquipController {
    @Autowired
    IGoodService goodService;

    @GetMapping("/equip/selectAll/{pageNum}")
    public ResultData<PageVo<GoodRawVo>> findGoodVoAll(@PathVariable("pageNum") Integer pageNum) {
        PageVo<GoodRawVo> pageVo = goodService.selectGoodRawVoByTypeId(pageNum,3);
        ResultData<PageVo<GoodRawVo>> resultData = new ResultData<>();
        if (pageVo.getTotal() == 0) {
            resultData.setCode(201);
            resultData.setMessage("没有任何记录");
        } else {
            resultData.setData(pageVo);
            resultData.setCode(200);
            resultData.setMessage("查询成功");
        }
        return resultData;
    }
    @PostMapping("/equip")
    public ResultData insertGood(Good good , MultipartFile[] files){
        ResultData<Object> resultData = new ResultData<>();
        //设置类型id
        good.setGoodTypeId(3);
        //处理图片资源
        try {
            if (files != null) {
                StringBuffer sb = new StringBuffer();
                for (MultipartFile file : files) {
                    if (file != null) {
                        String newFileName = UUID.randomUUID().toString() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                        log.info(newFileName);
                        sb.append("/pic/" + newFileName + ",");
                        file.transferTo(new File("D:\\images\\" + newFileName));
                    }
                }
                String pic = sb.substring(0, sb.length() - 1);
                good.setPic(pic);
            }
        }catch (Exception ex){
            throw new FileUpLoadException("文件上传异常");
        }
        Boolean res = goodService.addGoodRaw(good);
        if (res) {
            resultData.setCode(200);
            resultData.setMessage("添加成功");
        } else {
            resultData.setCode(402);
            resultData.setMessage("添加失败，发生未知异常");
        }
        return resultData;
    }
    @GetMapping("/equip/query")
    public ResultData<PageVo<GoodRawVo>> findGoodVoAllByQuery(GoodQuery goodQuery){
        goodQuery.setGoodTypeId(3);
        ResultData<PageVo<GoodRawVo>> resultData = new ResultData<>();
        PageVo<GoodRawVo> pageVo = goodService.selectGoodRawByGoodQuery(goodQuery);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(402);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }
    @GetMapping("/equip/warehouse/{pageNum}")
    public ResultData<PageVo<StockVo>> findGoodVoAllByWarehouseId(@PathVariable("pageNum")Integer pageNum){
        ResultData<PageVo<StockVo>> resultData = new ResultData<>();
        PageVo<StockVo> pageVo = goodService.selectGoodRaw(pageNum);
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

    @GetMapping("/equip/warehouse")
    public ResultData<PageVo<StockVo>> findStockByQuery(EquipQueryVo queryVo){
        ResultData<PageVo<StockVo>> resultData = new ResultData<>();
        PageVo<StockVo> pageVo = goodService.selectStockByEquipQuery(queryVo.getPageNum(),queryVo.getGoodName(),queryVo.getWarehouseName());
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
}
