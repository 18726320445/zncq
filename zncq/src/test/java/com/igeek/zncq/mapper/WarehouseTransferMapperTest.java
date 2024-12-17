package com.igeek.zncq.mapper;

import com.igeek.zncq.vo.WarehouseQueryVo;
import com.igeek.zncq.vo.WarehouseTransferQueryVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class WarehouseTransferMapperTest {
    @Autowired
    WarehouseTransferMapper warehouseTransferMapper;
    @Test
    void findAll() {
        warehouseTransferMapper.findAll();
    }

    @Test
    void findAllByQuery() {
        WarehouseTransferQueryVo warehouseTransferQueryVo = new WarehouseTransferQueryVo();
        warehouseTransferQueryVo.setGoodName("大");
        warehouseTransferMapper.findAllByQuery(warehouseTransferQueryVo);
    }
}