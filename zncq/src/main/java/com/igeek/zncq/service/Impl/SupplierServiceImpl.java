package com.igeek.zncq.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.Good;
import com.igeek.zncq.entity.GoodExample;
import com.igeek.zncq.entity.Supplier;
import com.igeek.zncq.entity.SupplierExample;
import com.igeek.zncq.exception.DeleteException;
import com.igeek.zncq.mapper.GoodMapper;
import com.igeek.zncq.mapper.SupplierMapper;
import com.igeek.zncq.service.ISupplierService;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.SupplierQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/10 12:41
 * @email liuyia2022@163.com
 */
@Transactional(rollbackFor = {})
@Service
public class SupplierServiceImpl implements ISupplierService {
    @Autowired
    SupplierMapper supplierMapper;
    @Autowired
    GoodMapper goodMapper;

    @Override
    public PageVo<Supplier> selectSupplierAll(Integer pageNum) {
        PageVo<Supplier> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum, 7);
        List<Supplier> suppliers = supplierMapper.selectByExampleWithBLOBs(new SupplierExample());
        PageInfo<Supplier> pageInfo = new PageInfo<>(suppliers, 5);
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setData(pageInfo.getList());
        return pageVo;
    }

    @Override
    public List<Supplier> selectSupplierAll() {
        List<Supplier> suppliers = supplierMapper.selectByExample(new SupplierExample());
        return suppliers;
    }

    @Override
    public Supplier selectSupplierOneById(Integer id) {
        Supplier supplier = supplierMapper.selectByPrimaryKey(id);
        return supplier;
    }

    @Override
    public PageVo<Supplier> selectSupplierAllBySupplierQueryVo(SupplierQueryVo queryVo) {
        PageVo<Supplier> pageVo = new PageVo<>();
        PageHelper.startPage(queryVo.getPageNum(), 7);
        SupplierExample supplierExample = new SupplierExample();
        SupplierExample.Criteria criteria = supplierExample.createCriteria();
        if (queryVo.getAddress() != null && !queryVo.getAddress().equals("")) {
            criteria.andAddressLike("%" + queryVo.getAddress() + "%");
        }
        if (queryVo.getName() != null && !queryVo.getName().equals("")) {
            criteria.andNameLike("%" + queryVo.getName() + "%");
        }
        List<Supplier> suppliers = supplierMapper.selectByExampleWithBLOBs(supplierExample);
        PageInfo<Supplier> pageInfo = new PageInfo<>(suppliers, 5);
        pageVo.setData(pageInfo.getList());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        pageVo.setTotal((int) pageInfo.getTotal());
        return pageVo;
    }

    @Override
    public Boolean insertSupplier(Supplier supplier) {
        int insert = supplierMapper.insert(supplier);
        return insert == 1;
    }

    @Override
    public Boolean deleteSupplierByIds(Integer[] ids) {
        //查看货物中是否有对应的供应商
        GoodExample goodExample = new GoodExample();
        goodExample.createCriteria().andSupplierIdIn(Arrays.asList(ids));
        int count = goodMapper.countByExample(goodExample);
        if (count != 0) {
            throw new DeleteException("你删除的供应商被货物占有，请先核对后在删除");
        }
        SupplierExample supplierExample = new SupplierExample();
        supplierExample.createCriteria().andIdIn(Arrays.asList(ids));
        int result = supplierMapper.deleteByExample(supplierExample);
        return result != 0;
    }

    @Override
    public Boolean updateSupplierById(Supplier supplier) {
        int result = supplierMapper.updateByPrimaryKeySelective(supplier);
        return result != 0;
    }
}
