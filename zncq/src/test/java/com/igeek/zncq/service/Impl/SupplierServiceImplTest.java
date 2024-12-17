package com.igeek.zncq.service.Impl;

import com.igeek.zncq.entity.Supplier;
import com.igeek.zncq.service.ISupplierService;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.SupplierQueryVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SupplierServiceImplTest {
    @Autowired
    ISupplierService supplierService;
    @Test
    void selectSupplierAll() {
        PageVo<Supplier> pageVo = supplierService.selectSupplierAll(1);
        System.out.println(pageVo);
    }

    @Test
    void insertSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName("俊伟集团");
        supplier.setAddress("江苏无锡国家软件园");
        supplier.setPhone("1888188818");
        supplier.setEmail("123456@qq.com");
        supplier.setLinkman("王俊伟");
        supplier.setRemarks("做大做强再创辉煌");
        System.out.println(supplierService.insertSupplier(supplier));
    }

    @Test
    void deleteSupplierByIds() {
        Boolean aBoolean = supplierService.deleteSupplierByIds(new Integer[]{2});
        System.out.println(aBoolean);
    }

    @Test
    void selectSupplierOneById() {
        System.out.println(supplierService.selectSupplierOneById(2));
    }

    @Test
    void updateSupplierById() {
        Supplier supplier = new Supplier();
        supplier.setId(10);
        supplier.setAddress("不知道啊");
        supplier.setRemarks("这是一个测试");
        System.out.println(supplierService.updateSupplierById(supplier));
    }

    @Test
    void selectSupplierAllBySupplierQueryVo() {
        SupplierQueryVo supplierQueryVo = new SupplierQueryVo();
        supplierQueryVo.setPageNum(1);
        supplierQueryVo.setAddress("知");
        System.out.println(supplierService.selectSupplierAllBySupplierQueryVo(supplierQueryVo));
    }

    @Test
    void testSelectSupplierAll() {
        List<Supplier> suppliers = supplierService.selectSupplierAll();
        System.out.println(suppliers);
    }


}