package com.igeek.zncq.controller;

import com.igeek.zncq.entity.Container;
import com.igeek.zncq.entity.Good;
import com.igeek.zncq.entity.Warehouse;
import com.igeek.zncq.service.IContainerService;
import com.igeek.zncq.service.IWarehouseService;
import com.igeek.zncq.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/14 18:43
 * @email liuyia2022@163.com
 */
@RestController
@Slf4j
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    IWarehouseService warehouseService;
    @Autowired
    IContainerService containerService;
    @PreAuthorize("hasRole('ROLE_EMP')")
    @GetMapping("/use")
    public ResultData<List<Warehouse>> findAllByUse(){
        ResultData<List<Warehouse>> resultData = new ResultData<>();
        List<Warehouse> list = warehouseService.selectAllByUse();
        if (list != null){
            resultData.setCode(200);
            resultData.setData(list);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有查询到任何数据");
        }
        return resultData;
    }
    @PreAuthorize("hasRole('ROLE_EMP')")
    @GetMapping("/container/type/{warehouseId}/{type}")
    public ResultData<List<Container>> findGoodAllByType(@PathVariable("warehouseId") Integer warehouseId,@PathVariable("type") String type){
        ResultData<List<Container>> resultData = new ResultData<>();
        List<Container> list = warehouseService.findGoodAllByType(warehouseId,type);
        if (list != null){
            resultData.setCode(200);
            resultData.setData(list);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有查询到任何数据");
        }
        return resultData;
    }
    @PreAuthorize("hasRole('ROLE_EMP')")
    @GetMapping()
    public ResultData<List<Warehouse>> findAll(){
        ResultData<List<Warehouse>> resultData = new ResultData<>();
        List<Warehouse> list = warehouseService.selectAll();
        if (list != null){
            resultData.setCode(200);
            resultData.setData(list);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有查询到任何数据");
        }
        return resultData;
    }
    @PreAuthorize("hasRole('ROLE_EMP')")
    @GetMapping("/{pageNum}")
    public ResultData<PageVo<Warehouse>> selectWarehouse(@PathVariable("pageNum") Integer pageNum){
        ResultData<PageVo<Warehouse>> resultData = new ResultData<>();
        PageVo<Warehouse> pageVo = warehouseService.selectWarehouse(pageNum);
        if (pageVo.getTotal() != 0){
            resultData.setCode(200);
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("没有查询到任何数据");
        }
        return resultData;
    };
    @PreAuthorize("hasRole('ROLE_EMP')")
    @GetMapping("/query/{id}")
    public ResultData<Warehouse> selectWarehouseOneById(@PathVariable("id") Integer id){
        ResultData<Warehouse> resultData = new ResultData<>();
        Warehouse warehouse = warehouseService.selectWarehouseOneById(id);
        if (warehouse != null){
            resultData.setCode(200);
            resultData.setData(warehouse);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(500);
            resultData.setMessage("你需要的数据不存在或发生未知异常");
        }
        return resultData;
    }
    @PreAuthorize("hasRole('ROLE_EMP')")
    @GetMapping("/query")
    public ResultData<PageVo<Warehouse>> selectWarehouseByQuery(WarehouseQueryVo warehouseQueryVo){
        ResultData<PageVo<Warehouse>> resultData = new ResultData<>();
        PageVo<Warehouse> pageVo = warehouseService.selectWarehouseByQuery(warehouseQueryVo);
        if (pageVo.getTotal() != 0){
            resultData.setData(pageVo);
            resultData.setMessage("查询成功");
        }else {
            resultData.setMessage("没有任何数据");
        }
        resultData.setCode(200);
        return resultData;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResultData addWarehouse(@RequestBody Warehouse warehouse){
        ResultData<Object> resultData = new ResultData<>();
        warehouseService.insertWarehouse(warehouse);
        resultData.setCode(200);
        resultData.setMessage("添加成功");
        return resultData;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResultData deleteWarehouse(@RequestParam("ids") Integer[] ids){
        ResultData<Object> resultData = new ResultData<>();
        warehouseService.deleteWarehouseById(ids);
        resultData.setCode(200);
        resultData.setMessage("删除成功");
        return resultData;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    public ResultData updateWarehouseById(@RequestBody Warehouse warehouse){
        ResultData<Object> resultData = new ResultData<>();
        warehouseService.updateWarehouseById(warehouse);
        resultData.setCode(200);
        resultData.setMessage("修改成功");
        return resultData;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/container/{id}")
    public ResultData<WarehouseVo> addContainer(@PathVariable("id") Integer id){
        ResultData<WarehouseVo> resultData = new ResultData<>();
        WarehouseVo warehouseVo = warehouseService.selectContainerByWarehouseId(id);
        if (warehouseVo != null){
            resultData.setCode(200);
            resultData.setData(warehouseVo);
            resultData.setMessage("查询成功");
        }else{
            resultData.setCode(201);
            resultData.setMessage("该仓库暂时没有容器");
        }
        return resultData;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/container")
    public ResultData addContainerForWarehouse(@RequestBody WarehouseAndContainerVo warehouseAndContainerVo){
        ResultData<Object> resultData = new ResultData<>();
        warehouseService.insertWarehouseAndContainer(warehouseAndContainerVo);
        resultData.setCode(200);
        resultData.setMessage("添加成功");
        return resultData;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/container")
    public ResultData deleteWarehouseContainer(@RequestBody WarehouseAndContainerVo warehouseAndContainerVo){
        ResultData<Object> resultData = new ResultData<>();
        warehouseService.deleteWarehouseContainerById(warehouseAndContainerVo.getWarehouseId(),warehouseAndContainerVo.getContainerId());
        resultData.setCode(200);
        resultData.setMessage("删除成功");
        return resultData;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/container")
    public ResultData updateWarehouseContainer(@RequestBody WarehouseAndContainerVo warehouseAndContainerVo){
        ResultData<Object> resultData = new ResultData<>();
        warehouseService.updateWarehouseContainerById(warehouseAndContainerVo);
        resultData.setCode(200);
        resultData.setMessage("修改成功");
        return resultData;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/container/{warehouseId}/{containerId}")
    public ResultData<Good> findGoodByWidAndCid(@PathVariable("warehouseId")Integer warehouseId,@PathVariable("containerId") Integer containerId){
        ResultData<Good> resultData = new ResultData<>();
        Good good = warehouseService.findGoodByWidAndCid(warehouseId,containerId);
        if (good != null){
            resultData.setCode(200);
            resultData.setData(good);
        }else{
            resultData.setCode(201);
        }
        return resultData;
    }
}
