package com.igeek.zncq.controller;


import com.alibaba.excel.EasyExcel;
import com.igeek.zncq.entity.Good;
import com.igeek.zncq.exception.FileUpLoadException;
import com.igeek.zncq.log.LogSys;
import com.igeek.zncq.service.IGoodService;
import com.igeek.zncq.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/8 18:30
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@RequestMapping({"/good"})
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class GoodController {
    @Autowired
    IGoodService goodService;

    @GetMapping("/raw/selectAll/{pageNum}")
    public ResultData<PageVo<GoodRawVo>> findGoodVoAll(@PathVariable("pageNum") Integer pageNum) {
        PageVo<GoodRawVo> pageVo = goodService.goodRawDetail(pageNum);
        ResultData<PageVo<GoodRawVo>> resultData = new ResultData<>();
        for (GoodRawVo datum : pageVo.getData()) {
            log.info(datum.getPic());
        }
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

    @GetMapping("/raw/query")
    public ResultData<PageVo<GoodRawVo>> findGoodVoAllByQuery(GoodQuery goodQuery) {
        goodQuery.setGoodTypeId(1);
        ResultData<PageVo<GoodRawVo>> resultData = new ResultData<>();
        PageVo<GoodRawVo> pageVo = goodService.selectGoodRawByGoodQuery(goodQuery);
        if (pageVo.getTotal() != 0) {
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        } else {
            resultData.setCode(402);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }

    @GetMapping("/raw/{goodId}")
    public ResultData<Good> findGoodRawOne(@PathVariable("goodId") Integer goodId) {
        ResultData<Good> resultData = new ResultData<>();
        Good good = goodService.selectGoodRawById(goodId);
        if (good != null) {
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(good);
        } else {
            resultData.setCode(402);
            resultData.setMessage("没有任何记录或发生未知异常");
        }
        return resultData;
    }

    @GetMapping("/raw")
    public ResultData<List<Good>> findAll() {
        ResultData<List<Good>> resultData = new ResultData<>();
        List<Good> goods = goodService.selectRawAll();
        resultData.setCode(200);
        resultData.setData(goods);
        return resultData;
    }

    @GetMapping("/raw/query/purchase")
    public ResultData<List<Good>> findAll(@RequestParam("purchaseNo") String purchaseNo) {
        ResultData<List<Good>> resultData = new ResultData<>();
        List<Good> goods = goodService.selectRawAllByPurchaseNo(purchaseNo);
        resultData.setCode(200);
        resultData.setData(goods);
        return resultData;
    }

    @LogSys("添加原料")
    @PostMapping(value = "/raw")
    public ResultData insertGood(Good good, MultipartFile[] files) throws IOException {
        ResultData<Object> resultData = new ResultData<>();
        //设置类型id
        good.setGoodTypeId(1);
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
        } catch (Exception ex) {
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

    @DeleteMapping(value = "/raw")
    public ResultData deleteGoodRawByIds(@RequestParam("id") Integer[] id) {
        ResultData<Object> resultData = new ResultData<>();
        Integer res = goodService.deleteGoodRawByIds(id);
        if (res == id.length) {
            resultData.setCode(200);
            resultData.setMessage("删除成功");
        } else {
            resultData.setCode(402);
            resultData.setMessage("删除异常");
        }
        return resultData;
    }

    @PutMapping("/raw")
    public ResultData updateGoodRaw(Good good, MultipartFile[] files) throws IOException {
        ResultData<Object> resultData = new ResultData<>();
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
        } catch (Exception e) {
            throw new FileUpLoadException("文件上传异常");
        }
        Boolean res = goodService.updateGoodRawById(good);
        if (res) {
            resultData.setCode(200);
            resultData.setMessage("修改成功");
        } else {
            resultData.setCode(402);
            resultData.setMessage("修改失败,发生未知异常");
        }
        return resultData;
    }

    @GetMapping("/RawAndEquip")
    public ResultData<List<Good>> findAllByType() {
        ResultData<List<Good>> resultData = new ResultData<>();
        List<Good> goods = goodService.selectAllByTypeId(new Integer[]{1, 3});
        if (goods != null && goods.size() != 0) {
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(goods);
        } else {
            resultData.setCode(201);
            resultData.setMessage("没有货物信息");
        }
        return resultData;
    }

    @GetMapping("/selectAll/{pageNum}")
    public ResultData<PageVo<GoodRawVo>> findGoodVoAllByPage(@PathVariable("pageNum") Integer pageNum) {
        PageVo<GoodRawVo> pageVo = goodService.selectGoodRawVoByTypeId(pageNum, 2);
        ResultData<PageVo<GoodRawVo>> resultData = new ResultData<>();
        for (GoodRawVo datum : pageVo.getData()) {
            log.info(datum.getPic());
        }
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

    @PostMapping
    public ResultData insertGood2(Good good, MultipartFile[] files) {
        ResultData<Object> resultData = new ResultData<>();
        //设置类型id
        good.setGoodTypeId(2);
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
        } catch (Exception ex) {
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

    @GetMapping("/query")
    public ResultData<PageVo<GoodRawVo>> findGoodVoAllByQuery2(GoodQuery goodQuery) {
        goodQuery.setGoodTypeId(2);
        ResultData<PageVo<GoodRawVo>> resultData = new ResultData<>();
        PageVo<GoodRawVo> pageVo = goodService.selectGoodRawByGoodQuery(goodQuery);
        if (pageVo.getTotal() != 0) {
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        } else {
            resultData.setCode(402);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }

    @GetMapping("/type")
    public ResultData findGoodByType(@RequestParam("contractType") Integer typeId, @RequestParam(value = "ids", required = false) Integer[] ids) {
        ResultData resultData = new ResultData();
        Integer goodTypeId = null;
        if (typeId == 0) {
            goodTypeId = 3;
        } else {
            goodTypeId = 2;
        }
        List<Good> goods = goodService.selectByGoodTypeIdAndIsNotInGoodIds(goodTypeId, ids);
        if (goods == null || goods.size() == 0) {
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        } else {
            resultData.setCode(200);
            resultData.setData(goods);
            resultData.setMessage("查询成功");
        }
        return resultData;
    }

    @GetMapping("/goods")
    public ResultData findGoods() {
        ResultData<List<Good>> resultData = new ResultData<>();
        List<Good> goods = goodService.selectAllByTypeId(new Integer[]{2});
        if (goods != null && goods.size() != 0) {
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(goods);
        } else {
            resultData.setCode(201);
            resultData.setMessage("没有货物信息");
        }
        return resultData;
    }

    @GetMapping("/findAll")
    public ResultData<List<Good>> findAllGood() {
        ResultData<List<Good>> resultData = new ResultData<>();
        List<Good> goods = goodService.selectAll();
        if (goods != null && goods.size() != 0) {
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(goods);
        } else {
            resultData.setCode(201);
            resultData.setMessage("没有货物信息");
        }
        return resultData;
    }

    @GetMapping("/warning/{pageNum}")
    public ResultData<PageVo<StockVo>> warning(@PathVariable("pageNum") Integer pageNum) {
        ResultData<PageVo<StockVo>> resultData = new ResultData<>();
        PageVo<StockVo> pageVo = goodService.warning(pageNum);
        if (pageVo.getTotal() != 0) {
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        } else {
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }

    @PostMapping("inExcel")
    public ResultData inExcel(@RequestParam("file") MultipartFile file) throws IOException {
        goodService.inExcel(file);
        return new ResultData(200, "导入成功");
    }

    @GetMapping("/outExcel/{typeId}")
    public ResultData<String> outExcel(@PathVariable("typeId") Integer typeId) {
        List<Good> goods = goodService.selectAllByTypeId(new Integer[]{typeId});
        String fileName = UUID.randomUUID().toString();
        File file = new File("D:\\InExcelTest\\" + fileName + ".xlsx");
        try (
            OutputStream outputStream = new FileOutputStream(file);
        ) {
            EasyExcel.write(outputStream, Good.class).sheet().doWrite(goods);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultData<String>(200, "导出成功", fileName);
    }
}
