package com.igeek.zncq.mapper;

import com.igeek.zncq.vo.ContainerVo;
import com.igeek.zncq.vo.WarehouseAndContainerVo;
import com.igeek.zncq.vo.WarehouseVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class WarehouseMapperTest {
    @Autowired
    WarehouseMapper warehouseMapper;
    @Test
    void deleteByIdForWarehouseAndContainer() {
        warehouseMapper.deleteByIdForWarehouseAndContainer(new Integer[]{1,2});
    }

    @Test
    void selectWarehouseVoById() {
        WarehouseVo warehouseVo = warehouseMapper.selectWarehouseVoById(3);
        System.out.println(warehouseVo.getContainerVos().size());
        for (ContainerVo containerVo : warehouseVo.getContainerVos()) {
            System.out.println(containerVo.getId());
        }
    }

    @Test
    void insertWarehouseContainer() {
        WarehouseAndContainerVo warehouseAndContainerVo = new WarehouseAndContainerVo();
        warehouseAndContainerVo.setWarehouseId(3);
        warehouseAndContainerVo.setContainerId(8);
        warehouseAndContainerVo.setNum(100l);
        warehouseMapper.insertWarehouseContainer(warehouseAndContainerVo);
    }

    @Test
    void deleteWarehouseContainerById() {
        warehouseMapper.deleteWarehouseContainerById(3,8);
    }

    @Test
    void updateNumByWarehouseContainerId() {
        WarehouseAndContainerVo warehouseAndContainerVo = new WarehouseAndContainerVo();
        warehouseAndContainerVo.setWarehouseId(3);
        warehouseAndContainerVo.setContainerId(2);
        warehouseAndContainerVo.setNum(20l);
        warehouseMapper.updateNumByWarehouseContainerId(warehouseAndContainerVo);
    }

    @Test
    void selectContainerNumByWarehouseId() {
        warehouseMapper.selectContainerNumByWarehouseId(1,3);
    }

    @Test
    void countByWarn() {
        List<Integer> list = warehouseMapper.countByWarn();
        System.out.println(list);
    }
}