package com.igeek.zncq.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.igeek.zncq.entity.Customer;
import com.igeek.zncq.entity.CustomerExample;
import com.igeek.zncq.entity.OrderExample;
import com.igeek.zncq.exception.AddException;
import com.igeek.zncq.exception.DeleteException;
import com.igeek.zncq.exception.UpdateException;
import com.igeek.zncq.mapper.CustomerMapper;
import com.igeek.zncq.mapper.OrderMapper;
import com.igeek.zncq.service.ICustomerService;
import com.igeek.zncq.vo.CustomerQueryVo;
import com.igeek.zncq.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author 刘燚
 * @version v1.0.0
 * @Description TODO
 * @createDate：2023/2/25 09:58
 * @email liuyia2022@163.com
 */
@Service
@Transactional(rollbackFor = {})
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    OrderMapper orderMapper;

    @Override
    public PageVo<Customer> selectAllByPage(Integer pageNum) {
        PageVo<Customer> pageVo = new PageVo<>();
        PageHelper.startPage(pageNum, 7);
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andIsDeleteEqualTo(0);
        List<Customer> customers = customerMapper.selectByExample(customerExample);
        PageInfo<Customer> pageInfo = new PageInfo<>(customers, 5);
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public void insertCustomer(Customer customer) {
        int res = customerMapper.insertSelective(customer);
        if (res != 1) {
            throw new AddException("添加失败，服务器异常请重新操作");
        }
    }

    @Override
    public Customer selectOneById(Integer id) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andIsDeleteEqualTo(0).andIdEqualTo(id);
        List<Customer> customers = customerMapper.selectByExample(customerExample);
        return customers.size() == 0 ? null : customers.get(0);
    }

    @Override
    public void updateById(Customer customer) {
        int res = customerMapper.updateByPrimaryKeySelective(customer);
        if (res != 1) {
            throw new UpdateException("修改失败，服务器异常请重新操作");
        }
    }

    @Override
    public void deleteByIds(Integer[] ids) {
        if (ids == null || ids.length == 0) {
            throw new DeleteException("请选择删除企业信息");
        }
        //查看订单中该企业信息是否被正在使用
        int count = orderMapper.countOrderByCustomerIdsAndStates(ids, new Integer[]{0, 1, 2});
        if (count != 0) {
            throw new DeleteException("企业信息正在被其他订单使用,无法删除");
        }
        Customer customer = new Customer();
        customer.setIsDelete(1);
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andIdIn(Arrays.asList(ids));
        int res = customerMapper.updateByExampleSelective(customer, customerExample);
        if (res != ids.length) {
            throw new DeleteException("删除失败，服务器异常请重新操作");
        }
    }

    @Override
    public PageVo<Customer> selectAllByPageQuery(CustomerQueryVo queryVo) {
        PageVo<Customer> pageVo = new PageVo<>();
        CustomerExample customerExample = new CustomerExample();
        CustomerExample.Criteria criteria = customerExample.createCriteria();
        criteria.andIsDeleteEqualTo(0);
        if (StringUtils.hasLength(queryVo.getCustomerName())) {
            criteria.andCustomerNameLike("%" + queryVo.getCustomerName() + "%");
        }
        if (StringUtils.hasLength(queryVo.getAddress())) {
            criteria.andAddressLike("%" + queryVo.getAddress() +"%");
        }
        PageHelper.startPage(queryVo.getPageNum(), 7);
        List<Customer> customers = customerMapper.selectByExample(customerExample);
        PageInfo<Customer> pageInfo = new PageInfo<>(customers, 5);
        pageVo.setTotal((int) pageInfo.getTotal());
        pageVo.setData(pageInfo.getList());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setCurrentPage(pageInfo.getPageNum());
        return pageVo;
    }

    @Override
    public List<Customer> selectAll() {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andIsDeleteEqualTo(0);
        return customerMapper.selectByExample(customerExample);
    }
}
