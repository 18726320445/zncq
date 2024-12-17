package com.igeek.zncq.service;

import com.igeek.zncq.entity.Vehicle;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.VehicleQueryVo;

import java.util.List;

/**
 * @author liuyi
 */
public interface IVehicleService {
    /**
     * 查找对应页码的车辆信息
     * @param pageNum
     * @return
     */
    PageVo<Vehicle> selectAll(Integer pageNum);

    /**
     * 添加车辆信息
     * @param vehicle
     */
    void insertVehicle(Vehicle vehicle);

    /**
     * 根据Id 查找车辆信息
     * @param id
     * @return
     */
    Vehicle selectOneById(Integer id);

    /**
     * 根据id修改车辆信息
     * @param vehicle
     */
    void updateById(Vehicle vehicle);

    /**
     * 根据id批量删除
     * @param ids
     */
    void deleteById(Integer[] ids);

    /**
     * 根据条件查询对应的车辆信息
     * @param vehicleQueryVo
     * @return
     */
    PageVo<Vehicle> selectByQuery(VehicleQueryVo vehicleQueryVo);

    /**
     * 根据最大容量来查找对应的车辆
     * @param maxNum
     * @return
     */
    List<Vehicle> findByMaxNum(Long maxNum);

    /**
     * 查询所有可以运输的车辆
     * @return
     */
    List<Vehicle> findAll();

    /**
     * 查询所有正在运输的车辆
     * @return
     */
    List<Vehicle> findAll2();
}
