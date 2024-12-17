package com.igeek.zncq.service.Impl;

import com.igeek.zncq.service.IOutStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OutStorageServiceImplTest {
    @Autowired
    IOutStorageService outStorageService;
    @Test
    void findPreWeekGoodSumTopSeven() {
        Map<String, Integer> ma = outStorageService.findPreWeekGoodSumTopSeven();
        System.out.println(ma);
    }
}