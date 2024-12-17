package com.igeek.zncq.mapper;

import com.igeek.zncq.vo.OutStorageVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OutStorageMapperTest {
    @Autowired
    OutStorageMapper outStorageMapper;
    @Test
    void selectAllByPage() {
        List<OutStorageVo> outStorageVos = outStorageMapper.selectAllByPage();
        for (OutStorageVo outStorageVo : outStorageVos) {
            System.out.println(outStorageVo.getVehicle().getName());
        }
    }

    @Test
    void selectFinishOutStorage() {
        List<OutStorageVo> outStorageVos = outStorageMapper.selectFinishOutStorage();
    }

    @Test
    void selectPreWeekGoodSumTopSeven() {
        List<Map<String, Object>> maps = outStorageMapper.SelectPreWeekGoodSumTopSeven();
        System.out.println(maps);
    }
}