package com.igeek.zncq.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MapControllerTest {
    @Autowired
    MapController mapController;
    @Test
    void getMap() {

        for (Double bigDecimal : mapController.getMap("北京,天安门")) {
            System.out.println(bigDecimal.toString());
        }
    }
}