package com.igeek.zncq.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PurchaseMapperTest {
    @Autowired
    PurchaseMapper purchaseMapper;
    @Test
    void selectPurchaseVoByNo() {
        purchaseMapper.selectPurchaseVoByNo("B41FE806");
    }

    @Test
    void selectNumOrderGood() {
        purchaseMapper.selectNumOrderGood("325575c1-1dc3-43a5-b73e-31fb06b32e01",3);
    }

    @Test
    void updateNumOrderGood() {
        purchaseMapper.updateNumOrderGood("325575c1-1dc3-43a5-b73e-31fb06b32e01",3,20l);
    }

    @Test
    void selectOneByPurchaseNoAndGoodId() {
        purchaseMapper.selectOneByPurchaseNo("5A3A21CF");
    }
}