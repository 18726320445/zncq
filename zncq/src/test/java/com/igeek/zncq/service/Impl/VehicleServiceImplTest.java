package com.igeek.zncq.service.Impl;

import com.igeek.zncq.entity.Vehicle;
import com.igeek.zncq.service.IVehicleService;
import com.igeek.zncq.vo.PageVo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VehicleServiceImplTest {
    @Autowired
    IVehicleService vehicleService;

    @Test
    void selectAll() {
        PageVo<Vehicle> vehiclePageVo = vehicleService.selectAll(1);
        System.out.println(vehiclePageVo);
    }

    @Test
    void insertVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNo("浙A666666");
        vehicle.setAdmin("王俊伟");
        vehicle.setMaxNum(99999l);
        vehicle.setName("法拉利SF90");
        vehicle.setPhone("13999999999");
        vehicleService.insertVehicle(vehicle);
    }

    @Test
    void selectOneById() {
        System.out.println(vehicleService.selectOneById(1));
    }

    @Test
    void updateById() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(3);
        vehicle.setName("奥迪R8");
        vehicle.setAdmin("方昆");
        vehicle.setVehicleNo("沪A888888");
        vehicle.setMaxNum(5000l);
        vehicle.setPhone("13999999999");
        vehicleService.updateById(vehicle);
    }
}