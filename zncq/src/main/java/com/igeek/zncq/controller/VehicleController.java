package com.igeek.zncq.controller;

import com.igeek.zncq.entity.Vehicle;
import com.igeek.zncq.exception.FileUpLoadException;
import com.igeek.zncq.service.IVehicleService;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.ResultData;
import com.igeek.zncq.vo.VehicleQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/16 18:47
 * @email liuyia2022@163.com
 */
@Slf4j
@RestController
@PreAuthorize("hasRole('ROLE_VEHICLE')")
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    IVehicleService vehicleService;

    @GetMapping("/{pageNum}")
    public ResultData<PageVo<Vehicle>> findAll(@PathVariable("pageNum") Integer pageNum) {
        ResultData<PageVo<Vehicle>> resultData = new ResultData<>();
        PageVo<Vehicle> pageVo = vehicleService.selectAll(pageNum);
        if (pageVo.getTotal() != 0) {
            resultData.setCode(200);
            resultData.setMessage("查询成功");
            resultData.setData(pageVo);
        } else {
            resultData.setCode(201);
            resultData.setMessage("没有任何数据");
        }
        return resultData;
    }

    @GetMapping("/query/{id}")
    public ResultData findOneById(@PathVariable("id") Integer id) {
        ResultData<Object> resultData = new ResultData<>();
        Vehicle vehicle = vehicleService.selectOneById(id);
        if (vehicle != null) {
            resultData.setCode(200);
            resultData.setData(vehicle);
            resultData.setMessage("查询成功");
        } else {
            resultData.setCode(201);
            resultData.setMessage("没有该车辆信息，请刷新页面");
        }
        return resultData;
    }

    @GetMapping("/query")
    public ResultData<PageVo<Vehicle>> selectAllByQuery(VehicleQueryVo vehicleQueryVo) {
        ResultData<PageVo<Vehicle>> resultData = new ResultData<>();
        PageVo<Vehicle> pageVo = vehicleService.selectByQuery(vehicleQueryVo);
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

    @PostMapping
    public ResultData addVehicle(Vehicle vehicle, MultipartFile[] files) {
        ResultData<Object> resultData = new ResultData<>();
        if (files != null) {
            StringBuffer sb = new StringBuffer();
            Stream.of(files).forEach(file -> {
                String fileName = file.getOriginalFilename();
                String newFileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
                try {
                    file.transferTo(new File("D:\\images\\" + newFileName));
                    sb.append("/pic/" + newFileName + ",");
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new FileUpLoadException("文件上传异常");
                }
            });
            String pic = sb.substring(0, sb.length() - 1);
            vehicle.setPic(pic);
        }
        vehicleService.insertVehicle(vehicle);
        resultData.setCode(200);
        resultData.setMessage("添加成功");
        return resultData;
    }

    @PutMapping
    public ResultData updateById(Vehicle vehicle, MultipartFile[] files) {
        ResultData<Object> resultData = new ResultData<>();
        if (files != null) {
            StringBuffer sb = new StringBuffer();
            Stream.of(files).forEach(file -> {
                String fileName = file.getOriginalFilename();
                String newFileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
                try {
                    file.transferTo(new File("D:\\images\\" + newFileName));
                    sb.append("/pic/" + newFileName + ",");
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new FileUpLoadException("文件上传异常");
                }
            });
            String pic = sb.substring(0, sb.length() - 1);
            vehicle.setPic(pic);
        }
        vehicleService.updateById(vehicle);
        resultData.setCode(200);
        resultData.setMessage("修改成功");
        return resultData;
    }

    @DeleteMapping
    public ResultData deleteById(@RequestParam("ids") Integer[] ids) {
        ResultData resultData = new ResultData();
        vehicleService.deleteById(ids);
        resultData.setCode(200);
        resultData.setMessage("删除成功");
        return resultData;
    }

    @GetMapping
    public ResultData<List<Vehicle>> findVehicleByMaxNum(@RequestParam("maxNum") Long maxNum) {
        ResultData resultData = new ResultData<List<Vehicle>>();
        List<Vehicle> vehicles = vehicleService.findByMaxNum(maxNum);
        if (vehicles != null) {
            resultData.setCode(200);
            resultData.setData(vehicles);
            resultData.setMessage("查询成功");
        } else {
            resultData.setCode(201);
            resultData.setMessage("没有车辆信息，请刷新页面");
        }
        return resultData;
    }

    @GetMapping("/findAll")
    public ResultData<List<Vehicle>> findAll() {
        ResultData resultData = new ResultData<List<Vehicle>>();
        List<Vehicle> vehicles = vehicleService.findAll();
        if (vehicles != null) {
            resultData.setCode(200);
            resultData.setData(vehicles);
            resultData.setMessage("查询成功");
        } else {
            resultData.setCode(201);
            resultData.setMessage("没有车辆信息，请刷新页面");
        }
        return resultData;
    }

    @GetMapping("/findAll2")
    public ResultData<List<Vehicle>> findAll2(){
        ResultData resultData = new ResultData<List<Vehicle>>();
        List<Vehicle> vehicles = vehicleService.findAll2();
        if (vehicles != null) {
            resultData.setCode(200);
            resultData.setData(vehicles);
            resultData.setMessage("查询成功");
        } else {
            resultData.setCode(201);
            resultData.setMessage("没有车辆信息，请刷新页面");
        }
        return resultData;
    }
}
