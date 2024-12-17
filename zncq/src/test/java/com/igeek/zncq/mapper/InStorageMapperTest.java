package com.igeek.zncq.mapper;

import com.igeek.zncq.vo.InStorageQueryVo;
import com.igeek.zncq.vo.InStorageVo;
import com.igeek.zncq.vo.OrderVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class InStorageMapperTest {
    @Autowired
    InStorageMapper inStorageMapper;

    @Test
    void selectAll() {
        List<InStorageVo> inStorageVos = inStorageMapper.selectAll();
        inStorageVos.forEach(inStorageVo -> System.out.println(inStorageVo.getOrderNo()));
    }

    @Test
    void selectAllGoodByOrderNo() {
        OrderVo orderVo = inStorageMapper.selectAllGoodByOrderNo("3e8ecc48-e4af-4bf9-bc14-a334c0c0c057");
        System.out.println(orderVo);
    }

    @Test
    void selectAllByQuery() {
        InStorageQueryVo inStorageQueryVo = new InStorageQueryVo();
        inStorageQueryVo.setOrderNo("e");
        inStorageMapper.selectAllByQuery(inStorageQueryVo);
    }

    @Test
    void selectInStorageVoByInDate() {
        inStorageMapper.selectInStorageVoByInDate();
    }

    @Test
    void selectOneByOrderNo() {
        inStorageMapper.selectOneByOrderNo("3e8ecc48-e4af-4bf9-bc14-a334c0c0c057");
    }

    @Test
    void selectInStorageVoByQuery2() {
        InStorageQueryVo inStorageQueryVo = new InStorageQueryVo();
        inStorageQueryVo.setPageNum(1);
        inStorageQueryVo.setGoodName("大");
        inStorageQueryVo.setOrderNo("e4af");
        inStorageMapper.selectInStorageVoByQuery2(inStorageQueryVo);
    }

    @Test
    void selectGoodVoByOrderNo() {
        inStorageMapper.selectGoodVoByOrderNo("710e5188-8e5f-4b76-a27c-d3c0de8ca342");
    }

    @Test
    void selectGoodVoByInStorageOrderNo() {
        inStorageMapper.selectGoodVoByInStorageOrderNo("3e8ecc48-e4af-4bf9-bc14-a334c0c0c057");
    }
}