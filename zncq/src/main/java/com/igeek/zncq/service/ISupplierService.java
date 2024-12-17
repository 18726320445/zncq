package com.igeek.zncq.service;

import com.igeek.zncq.entity.Supplier;
import com.igeek.zncq.vo.PageVo;
import com.igeek.zncq.vo.SupplierQueryVo;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * @author liuyi
 */
public interface ISupplierService {
    /**
     * 查询该页面所有供应商的信息
     * @param pageNum 当前页面
     * @return
     */
    PageVo<Supplier> selectSupplierAll(Integer pageNum);

    /**
     * 查询所有的供应商信息
     * @return
     */
    List<Supplier> selectSupplierAll();

    /**
     * 对应id的供应商的信息
     * @param id 供应商id
     * @return
     */
    Supplier selectSupplierOneById(Integer id);

    /**
     * 根据查找条件查找供应商
     * @param queryVo
     * @return
     */
    PageVo<Supplier> selectSupplierAllBySupplierQueryVo(SupplierQueryVo queryVo);
    /**
     * 添加供应商
     * @param supplier
     * @return
     */
    Boolean insertSupplier(Supplier supplier);

    /**
     * 根据id删除供应商记录
     * @param ids
     * @return
     */
    Boolean deleteSupplierByIds(Integer[] ids);

    /**
     * 根据Id修改供应商信息
     * @param supplier
     * @return
     */
    Boolean updateSupplierById(Supplier supplier);
}
