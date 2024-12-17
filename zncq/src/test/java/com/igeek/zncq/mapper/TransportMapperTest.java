package com.igeek.zncq.mapper;

import com.igeek.zncq.vo.TransportQueryVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TransportMapperTest {
    @Autowired
    TransportMapper transportMapper;
    @Test
    void selectAllByGtState() {
        transportMapper.selectAllByGtState(1);
    }

    @Test
    void selectAllByGtStateQuery() {
        transportMapper.selectAllByGtStateQuery(new TransportQueryVo());
    }
}