package com.igeek.zncq.service.Impl;

import com.igeek.zncq.entity.Good;
import com.igeek.zncq.service.IGoodService;
import com.igeek.zncq.vo.GoodQuery;
import com.igeek.zncq.vo.GoodRawVo;
import com.igeek.zncq.vo.PageVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GoodServiceImplTest {
    @Autowired
    IGoodService goodService;
    @Test
    void goodDetail() {
        PageVo<GoodRawVo> pageVo = goodService.goodRawDetail(5);
        for (GoodRawVo datum : pageVo.getData()) {
            System.out.println(datum);
        }
        System.out.println(pageVo.getPageSize());
        System.out.println(pageVo.getCurrentPage());
        System.out.println(pageVo.getTotal());
    }

    @Test
    void addGoodRaw() {

        Good good = new Good();
        good.setGoodName("西瓜");
        good.setGoodTypeId(1);
        good.setPrice(12.8);
        good.setDetail("无籽西瓜吃到爽");
        good.setSupplierId(2);
        for (int i = 0; i < 10; i++) {
            System.out.println(goodService.addGoodRaw(good));
        }
    }

    @Test
    void deleteGoodRawByIds() {
        System.out.println(goodService.deleteGoodRawByIds(new Integer[]{6}));
    }

    @Test
    void selectGoodRawById() {
        System.out.println(goodService.selectGoodRawById(11).getDetail());
    }

    @Test
    void updateGoodRawById() {
        Good good = new Good();
        good.setGoodId(23);
        good.setDetail("茉莉花真的好香啊");
        System.out.println(goodService.updateGoodRawById(good));
    }

    @Test
    void selectGoodRawByGoodQuery() {
        GoodQuery goodQuery = new GoodQuery();
        goodQuery.setPageNum(1);
        goodQuery.setGoodTypeId(1);
        goodQuery.setGoodName("桔");
        PageVo<GoodRawVo> pageVo = goodService.selectGoodRawByGoodQuery(goodQuery);
        for (GoodRawVo datum : pageVo.getData()) {
            System.out.println(datum);
        }
    }

    @Test
    void selectRawAll() {
        List<Good> goods = goodService.selectRawAll();
    }
}