package com.igeek.zncq.mapper;

import com.igeek.zncq.vo.GoodQuery;
import com.igeek.zncq.vo.GoodRawVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GoodMapperTest {
    @Autowired
    GoodMapper goodMapper;
    @Test
    void selectGoodVoAll() {
        List<GoodRawVo> goodVos = goodMapper.selectGoodForRawVoAll();
        for (GoodRawVo goodVo : goodVos) {
            System.out.println(goodVo.getDetail());
        }
    }

    @Test
    void selectGoodRawByGoodQuery() {
        GoodQuery goodQuery = new GoodQuery();
        goodQuery.setGoodTypeId(1);
        goodQuery.setSupplierName("俊伟");
        System.out.println(goodMapper.selectGoodRawByGoodQuery(goodQuery));
    }

    @Test
    void countByGoodIdAndOrder() {
        System.out.println(goodMapper.countByGoodIdAndOrder(new Integer[]{2}));
    }
}