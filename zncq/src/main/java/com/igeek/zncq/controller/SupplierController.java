package com.igeek.zncq.controller;

import com.igeek.zncq.entity.Supplier;
import com.igeek.zncq.service.ISupplierService;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.ResultData;
import com.igeek.zncq.vo.SupplierQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/10 12:34
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@RequestMapping({"/supplier"})
public class SupplierController {
    @Autowired
    ISupplierService supplierService;

    @GetMapping("/{pageNum}")
    public ResultData<PageVo<Supplier>> selectAll(@PathVariable("pageNum") Integer pageNum) {
        ResultData<PageVo<Supplier>> resultData = new ResultData<>();
        PageVo<Supplier> pageVo = supplierService.selectSupplierAll(pageNum);
        if (pageVo.getTotal() == 0) {
            resultData.setCode(201);
            resultData.setMessage("没有如何记录");
        } else {
            resultData.setData(pageVo);
            resultData.setCode(200);
            resultData.setMessage("查询成功");
        }
        return resultData;
    }

    @PostMapping
    public ResultData addSupplier(@RequestBody Supplier supplier){
        ResultData resultData = new ResultData<>();
        Boolean result = supplierService.insertSupplier(supplier);
        if (result){
            resultData.setCode(200);
            resultData.setMessage("添加成功");
        }else{
            resultData.setCode(402);
            resultData.setMessage("添加失败，发生未知异常");
        }
        return resultData;
    }

    @DeleteMapping
    public ResultData deleteSupplier(@RequestParam("id") Integer[] id){
        ResultData<Object> resultData = new ResultData<>();
        Boolean result = supplierService.deleteSupplierByIds(id);
        if (result){
            resultData.setCode(200);
            resultData.setMessage("删除成功");
        }else{
            resultData.setCode(402);
            resultData.setMessage("删除失败，发生未知异常");
        }
        return resultData;
    }

    @GetMapping("/supplierOne/{id}")
    public ResultData<Supplier> selectSupplierForOne(@PathVariable("id") Integer id){
        ResultData<Supplier> resultData = new ResultData<>();
        Supplier supplier = supplierService.selectSupplierOneById(id);
        if (supplier != null){
            resultData.setCode(200);
            resultData.setData(supplier);
            resultData.setMessage("查询成功");
        }else {
            resultData.setCode(402);
            resultData.setMessage("查询失败,发生未知异常");
        }
        return resultData;
    }
    @PutMapping
    public ResultData updateSupplier(@RequestBody Supplier supplier){
        ResultData<Object> resultData = new ResultData<>();
        Boolean result = supplierService.updateSupplierById(supplier);
        if (result){
            resultData.setCode(200);
            resultData.setMessage("修改成功");
        }else{
            resultData.setCode(401);
            resultData.setMessage("修改失败，发生未知异常");
        }
        return resultData;
    }

    @GetMapping("/query")
    public ResultData<PageVo<Supplier>> selectSupplierBySupplierQueryVo(SupplierQueryVo queryVo){
        ResultData<PageVo<Supplier>> resultData = new ResultData<>();
        PageVo<Supplier> pageVo = supplierService.selectSupplierAllBySupplierQueryVo(queryVo);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有如何数据");
        }
        return resultData;
    }

    @GetMapping("/all")
    public ResultData<List<Supplier>> selectSupplier(){
        ResultData<List<Supplier>> resultData = new ResultData<>();
        List<Supplier> suppliers = supplierService.selectSupplierAll();
        if (suppliers.size() != 0){
            for (Supplier supplier : suppliers) {
                supplier.setAddress(null);
                supplier.setLinkman(null);
                supplier.setPhone(null);
                supplier.setEmail(null);
            }
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(suppliers);
        }else{
            resultData.setCode(402);
            resultData.setMessage("没有如何数据或数据加载异常");
        }
        return resultData;
    }
}
