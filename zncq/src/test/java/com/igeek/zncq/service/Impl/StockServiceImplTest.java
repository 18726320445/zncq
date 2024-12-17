package com.igeek.zncq.service.Impl;

import com.igeek.zncq.service.IStockService;
import com.igeek.zncq.vo.StockVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class StockServiceImplTest {
    @Autowired
    IStockService stockService;

    @Test
    void findAllRawByGoodType() {
        Map<String, Integer> allRawByGoodType = stockService.findAllRawByGoodType(1);
        System.out.println(allRawByGoodType);
    }

    @Test
    void warn() {
        List<StockVo> warn = stockService.warn();
        warn.forEach(stockVo -> System.out.println(stockVo.getNum()));
    }
}