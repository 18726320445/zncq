package com.igeek.zncq.service;

import com.igeek.zncq.entity.Customer;
import com.igeek.zncq.vo.CustomerQueryVo;
import com.igeek.zncq.vo.PageVo;

import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/25 09:57
 * @email liuyia2022@163.com
 */
public interface ICustomerService {
    /**
     * 根据页码查询客户信息
     * @param pageNum
     * @return
     */
    PageVo<Customer> selectAllByPage(Integer pageNum);

    /**
     * 添加客户
     * @param customer
     */
    void insertCustomer(Customer customer);

    /**
     * 根据Id查找客户信息
     * @param id
     * @return
     */
    Customer selectOneById(Integer id);

    /**
     * 根据Id 更新客户信息
     * @param customer
     */
    void updateById(Customer customer);

    /**
     * 批量删除
     * @param ids
     */
    void deleteByIds(Integer[] ids);

    /**
     * 根据条件查找
     * @param queryVo
     * @return
     */
    PageVo<Customer> selectAllByPageQuery(CustomerQueryVo queryVo);

    /**
     * 查询所有客户企业信息
     * @return
     */
    List<Customer> selectAll();
}
