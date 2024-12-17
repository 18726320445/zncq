package com.igeek.zncq.service.Impl;

import com.igeek.zncq.entity.Container;
import com.igeek.zncq.entity.InStorage;
import com.igeek.zncq.entity.InStorageExample;
import com.igeek.zncq.mapper.ContainerMapper;
import com.igeek.zncq.mapper.InStorageMapper;
import com.igeek.zncq.service.IContainerService;
import com.igeek.zncq.vo.ContainerQueryVo;
import com.igeek.zncq.vo.PageVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ContainerServiceImplTest {
    @Autowired
    IContainerService containerService;
    @Autowired
    InStorageMapper inStorageMapper;

    @Test
    void selectContainAll() {
        PageVo<Container> containerPageVo = containerService.selectContainAll(1);
        System.out.println(containerPageVo);
    }

    @Test
    void insertContainer() {
        Container container = new Container();
        container.setName("设备容器");
        container.setMaxCapacity(2000l);
        container.setType("设备");
        containerService.insertContainer(container);
    }

    @Test
    void selectOne() {
        Container container = containerService.selectOne(1);
        System.out.println(container);
    }

    @Test
    void updateById() {
        Container container = new Container();
        container.setId(3);
        container.setName("修改测试");
        container.setMaxCapacity(50000l);
        containerService.updateById(container);
    }

    @Test
    void deleteContainerById() {
        containerService.deleteContainerById(new Integer[]{6,7});
    }

    @Test
    void selectByQuery() {
        ContainerQueryVo containerQueryVo = new ContainerQueryVo();
        containerQueryVo.setPageNum(1);
        containerQueryVo.setName("测");
        PageVo<Container> pageVo = containerService.selectByQuery(containerQueryVo);
        System.out.println(pageVo);
    }

    @Test
    void selectAll() {
        List<Container> containers = containerService.selectAll(new Integer[]{1,2,3,4});
    }

    @Test
    void test(){
        InStorageExample inStorageExample = new InStorageExample();
        inStorageExample.createCriteria().andOrderNoEqualTo("3e69303a-57ab-47cb-bbd8-424062a10a90").andGoodIdEqualTo(3)
                .andIndateIsNull();
        List<InStorage> inStorages = inStorageMapper.selectByExample(inStorageExample);
        System.out.println(inStorages.get(0).getIndate());
    }
}