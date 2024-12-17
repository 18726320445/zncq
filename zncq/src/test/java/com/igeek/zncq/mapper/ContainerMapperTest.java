package com.igeek.zncq.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ContainerMapperTest {
    @Autowired
    ContainerMapper containerMapper;
    @Test
    void countById() {
        System.out.println(containerMapper.countByIdWarehouseContainer(new Integer[]{2}));
    }

    @Test
    void deleteByIdWarehouseContainer() {
        containerMapper.deleteByIdWarehouseContainer(new Integer[]{7});
    }
}