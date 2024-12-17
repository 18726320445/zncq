package com.igeek.zncq.service.Impl;

import com.igeek.zncq.entity.Warehouse;
import com.igeek.zncq.service.IWarehouseService;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.WarehouseQueryVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class WarehouseServiceImplTest {
    @Autowired
    IWarehouseService warehouseService;
    @Test
    void selectWarehouse() {
        PageVo<Warehouse> warehousePageVo = warehouseService.selectWarehouse(1);
        for (Warehouse datum : warehousePageVo.getData()) {
            System.out.println(datum.getName());
        }
        warehousePageVo.getTotal();
    }

    @Test
    void insertWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setName("大飞老师的仓库");
        warehouse.setAdmin("Faker");
        warehouse.setAddress("韩国");
        warehouse.setPhone("18888888888");
        warehouseService.insertWarehouse(warehouse);
    }

    @Test
    void deleteWarehouse() {
        warehouseService.deleteWarehouseById(new Integer[]{4,5});
    }

    @Test
    void selectWarehouseOneById() {
        System.out.println(warehouseService.selectWarehouseOneById(1));
    }

    @Test
    void selectWarehouseByQuery() {
        WarehouseQueryVo warehouseQueryVo = new WarehouseQueryVo();
        warehouseQueryVo.setPageNum(1);
        warehouseQueryVo.setName("啊");
        warehouseQueryVo.setAddress("上");
        PageVo<Warehouse> pageVo = warehouseService.selectWarehouseByQuery(warehouseQueryVo);
        System.out.println(pageVo);
    }

    @Test
    void deleteWarehouseContainerById() {
        warehouseService.deleteWarehouseContainerById(9,2);
    }
}