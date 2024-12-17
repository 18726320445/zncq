package com.igeek.zncq.service.Impl;

import com.igeek.zncq.entity.Purchase;
import com.igeek.zncq.mapper.PurchaseMapper;
import com.igeek.zncq.service.IPurchaseService;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.PurchaseDto;
import com.igeek.zncq.vo.PurchaseVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PurchaseServiceImplTest {
    @Autowired
    IPurchaseService purchaseService;
    @Autowired
    PurchaseMapper purchaseMapper;
    @Test
    void insertPurchase() {
        Purchase purchase = new Purchase();
        purchase.setProcessName("王俊伟");
        purchaseService.insertPurchase(purchase);
    }

    @Test
    void updatePurchaseGoodByQuery() {
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setPurchaseNo("15787CA8");
        purchaseDto.setGoodId(2);
        purchaseDto.setNum(9l);
        purchaseService.updatePurchaseGoodByQuery(purchaseDto);
    }

    @Test
    void updateState() {

        purchaseService.updateState("FAFEA5AA");
    }

    @Test
    void findAllByQuery() throws ParseException {
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setPageNum(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date parse = sdf.parse("2023-02-20 20:50:00");
        purchaseDto.setToDate(parse);
        PageVo<PurchaseVo> allByQuery = purchaseService.findAllByQuery(purchaseDto);
    }
}