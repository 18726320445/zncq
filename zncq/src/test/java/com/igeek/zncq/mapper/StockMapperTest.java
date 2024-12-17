package com.igeek.zncq.mapper;

import com.igeek.zncq.vo.StockQueryVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class StockMapperTest {
    @Autowired
    StockMapper stockMapper;
    @Test
    void selectAllByPage() {
        stockMapper.selectAllByPage();
    }

    @Test
    void selectAllByQuery() {
        StockQueryVo queryVo = new StockQueryVo();
        queryVo.setWarehouseName("急");
        stockMapper.selectAllByQuery(queryVo);
    }

    @Test
    void selectAll() {
        stockMapper.selectAllEquipByWC();
    }

    @Test
    void selectAllEquipByWCAndQuery() {
        stockMapper.selectAllEquipByWCAndQuery("方","");

    }

    @Test
    void findAllByGoodType() {
        List<Map<String, Object>> allByGoodType = stockMapper.findAllByGoodType(1);
        System.out.println(allByGoodType);
    }
}