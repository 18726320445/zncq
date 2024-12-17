package com.igeek.zncq.mapper;

import com.igeek.zncq.vo.OrderGoodVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderMapperTest {
    @Autowired
    OrderMapper orderMapper;
    @Test
    void insertOrderGood() {
        orderMapper.insertOrderGood("325575c1-1dc3-43a5-b73e-31fb06b32e01",3,30l);
    }

    @Test
    void countNumByOrderNoLong() {
        System.out.println(orderMapper.countNumByOrderNoLong("325575c1-1dc3-43a5-b73e-31fb06b32e01"));
    }

    @Test
    void selectOneByOrderNo() {
        orderMapper.selectOneByOrderNo("c1e73e10-58ed-4f6e-8aa2-637a43619829");
    }


    @Test
    void selectOrderGood() {
        OrderGoodVo orderGoodVo = orderMapper.selectOrderGood("0b7b2bfa-cc76-4a9b-ba51-d143a528453a", 3);
        System.out.println(orderGoodVo.getNum() + "===" + orderGoodVo.getVehicleId());
    }

    @Test
    void selectOrderVoAll() {
        orderMapper.selectOrderVoAll("","");
    }
}